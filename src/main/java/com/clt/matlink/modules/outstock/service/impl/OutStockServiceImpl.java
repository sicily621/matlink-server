package com.clt.matlink.modules.outstock.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.common.security.LoginHelper;
import com.clt.matlink.modules.base.common.domain.entity.Stock;
import com.clt.matlink.modules.base.common.domain.form.StockSaveParam;
import com.clt.matlink.modules.enums.MateriaAuditResourceTypeEnum;
import com.clt.matlink.modules.enums.MateriaAuditStatusEnum;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import com.clt.matlink.modules.flow.domain.form.AuditFlowRelationCurrentUserQuery;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationGenerateParam;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationGenerateResult;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationResult;
import com.clt.matlink.modules.flow.service.AuditFlowRelationService;
import com.clt.matlink.modules.instock.domain.entity.InStock;
import com.clt.matlink.modules.instock.domain.form.InStockSaveParam;
import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import com.clt.matlink.modules.outstock.domain.form.OutStockForm;
import com.clt.matlink.modules.outstock.domain.form.OutStockSaveParam;
import com.clt.matlink.modules.outstock.domain.vo.OutStockVo;
import com.clt.matlink.modules.outstock.mapper.OutStockMapper;
import com.clt.matlink.modules.outstock.service.OutStockDetailService;
import com.clt.matlink.modules.outstock.service.OutStockService;
import com.clt.matlink.modules.purchase.domain.entity.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OutStockServiceImpl implements OutStockService {
    @Autowired
    private OutStockMapper outStockMapper;
    @Autowired
    private OutStockDetailService outStockDetailService;
    @Autowired
    private AuditFlowRelationService auditFlowRelationService;
    @Override
    public OutStock save(OutStockSaveParam outStock) {
        int flag = 0;
        if(outStock.getId()==null){
            //生成审批流
            flag= outStockMapper.insert(outStock);
            MaterialAuditRelationGenerateParam generateParam = new MaterialAuditRelationGenerateParam();
            generateParam.setType(MateriaAuditResourceTypeEnum.MATERIAL_OUT_STOCK.getValue());
            generateParam.setStockId(outStock.getStockId());
            generateParam.setOrderId(outStock.getId());
            generateParam.setDeptId(outStock.getDeptId());


            MaterialAuditRelationGenerateResult generateResult = auditFlowRelationService.generateAuditFlowRelation(generateParam);
            AuditFlowRelation auditFlowRelation = generateResult.getAuditFlowRelation();
            outStock.setAuditStatus(auditFlowRelation.getAuditStatus());
            outStock.setAuditTime(auditFlowRelation.getAuditTime());
            outStock.setAuditUserId(auditFlowRelation.getAuditUserId());
            outStockMapper.updateById(outStock);
            // TODO 是否直接入库
        }else{
            flag = outStockMapper.updateById(outStock);
        }
        if(flag>0){
            return outStockMapper.selectById(outStock.getId());
        }else{
            return null;
        }
    }

    @Override
    public OutStock getById(Long id) {
        return outStockMapper.selectById(id);
    }

    @Override
    public List<OutStock> getByIds(List<Long> ids) {
        LambdaQueryWrapper<OutStock> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutStock::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( OutStock::getId, ids);
        return outStockMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        outStockMapper.deleteById(id);
        return true;
    }

    @Override
    public List<OutStock> list(OutStockForm form) {
        LambdaQueryWrapper<OutStock> lqw = getQueryWrapper(form);
        return outStockMapper.selectList(lqw);
    }

    @Override
    public PageInfo<OutStockVo> page(OutStockForm form, PageQuery pageQuery) {
        Long userId = LoginHelper.getLoginEmployeeId();

        LambdaQueryWrapper<OutStock> lqw = getQueryWrapper(form);
        Page<OutStock> page = pageQuery.build();
        Page<OutStock> result = outStockMapper.selectPage(page, lqw);
        PageInfo<OutStockVo> tableDataInfo = PageInfo.build(result, OutStockVo.class );
        List<OutStockVo> list = tableDataInfo.getList();

        AuditFlowRelationCurrentUserQuery flowRelationCurrentUserQuery = new AuditFlowRelationCurrentUserQuery();
        flowRelationCurrentUserQuery.setType(MateriaAuditResourceTypeEnum.MATERIAL_OUT_STOCK.getValue());
        flowRelationCurrentUserQuery.setStockId(form.getStockId());
        List<Long> beingOrderIds = auditFlowRelationService.listAuditBeingOrderIdsByCurrentUser(flowRelationCurrentUserQuery);
        for (OutStockVo outStockVo : list) {
            if(beingOrderIds.contains(outStockVo.getId())){
                outStockVo.setHasAuditAuth(true);//当前登陆人有待审批
            }
            if (outStockVo.getOutStockUserId().equals(userId)) {
                outStockVo.setHasOutStockAuth(true);//当前登陆人有入库权限
            }
            BigDecimal total = outStockDetailService.findOutStockAmount(outStockVo.getId());
            outStockVo.setOutStockAmount(total);
        }
        return tableDataInfo;
    }

    @Override
    public List<OutStock> batchSave(List<OutStock> auditFlowDetails) {
        outStockMapper.insertOrUpdateBatch(auditFlowDetails);
        List<Long> list = CollStreamUtil.toList(auditFlowDetails, OutStock::getId);
        List<OutStock> result = getByIds(list);
        return result;
    }
    @Override
    public OutStock updateAuditStatus(MaterialAuditRelationParam generateParam) {
        OutStock old = this.getById(generateParam.getOrderId());
        if (old == null) {
            throw new ServiceException("出库单不存在");
        }
        if (old.getStatus() != null &&  old.getStatus() == 2){
            throw new ServiceException("出库单已作废");
        }
        if (old.getStatus() != null &&  old.getStatus() == 1){
            throw new ServiceException("出库单已出库");
        }

        //处理审批
        MaterialAuditRelationResult auditResult = auditFlowRelationService.processAuditFlowRelation(generateParam);
        AuditFlowRelation flowRelation = auditResult.getFlowRelation();
        // 更新审批状态
        OutStockSaveParam entity = new OutStockSaveParam();
        entity.setId(flowRelation.getOrderId());
        entity.setAuditStatus(flowRelation.getAuditStatus());
        entity.setAuditTime(flowRelation.getAuditTime());
        entity.setAuditUserId(generateParam.getCurrentUserId());
        this.save(entity);
        //审批通过
        if (entity.getAuditStatus().equals(MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus())) {
            // TODO 判断是否直接出库
//            dealDirectInStock(entity.getId());
        }
        return this.getById(old.getId());
    }

    private LambdaQueryWrapper<OutStock> getQueryWrapper(OutStockForm form) {

        LambdaQueryWrapper<OutStock> lqw = Wrappers.lambdaQuery();
        lqw.eq(form.getStockId()!=null, OutStock::getStockId, form.getStockId());
        lqw.eq(form.getStatus()!=null, OutStock::getStatus, form.getStatus());
        lqw.eq(form.getAuditStatus()!=null, OutStock::getAuditStatus, form.getAuditStatus());
        lqw.ge(form.getStartTime()!=null, OutStock::getOutStockTime, form.getStartTime());
        lqw.le(form.getEndTime()!=null, OutStock::getOutStockTime, form.getEndTime());
        lqw.eq( OutStock::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
    @Override
    public Boolean validateOutStockNo(OutStock outStock) {
        if (outStock.getOutStockNo() == null || outStock.getOutStockNo().trim().isEmpty()) {
            return false;
        }
        LambdaQueryWrapper<OutStock> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutStock::getOutStockNo, outStock.getOutStockNo());
        lqw.eq(OutStock::getDelFlag, DelFlagEnum.NORMAL.getValue());
        if (outStock.getId() != null) {
            // 【编辑场景】：排除当前这条记录本身
            // 逻辑：查找 (code相同 AND 未删除 AND id != 当前id) 的记录
            lqw.ne(OutStock::getId, outStock.getId());
        }
        long count = outStockMapper.selectCount(lqw);
        return count == 0;
    }
}
