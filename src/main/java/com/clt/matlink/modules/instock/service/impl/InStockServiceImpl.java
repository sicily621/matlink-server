package com.clt.matlink.modules.instock.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.security.EmployeeHelper;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import com.clt.matlink.modules.flow.domain.enums.MateriaAuditResourceTypeEnum;
import com.clt.matlink.modules.flow.domain.form.AuditFlowRelationCurrentUserQuery;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationGenerateParam;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationGenerateResult;
import com.clt.matlink.modules.flow.service.AuditFlowRelationService;
import com.clt.matlink.modules.instock.domain.entity.InStock;
import com.clt.matlink.modules.instock.domain.form.InStockForm;
import com.clt.matlink.modules.instock.domain.form.InStockSaveParam;
import com.clt.matlink.modules.instock.domain.vo.InStockVo;
import com.clt.matlink.modules.instock.mapper.InStockMapper;
import com.clt.matlink.modules.instock.service.InStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InStockServiceImpl implements InStockService {
    @Autowired
    private InStockMapper inStockMapper;
    @Autowired
    private AuditFlowRelationService auditFlowRelationService;
    @Override
    public InStock save(InStockSaveParam inStock) {
        int flag = 0;
        if(inStock.getId()==null){
            //生成审批流
            flag= inStockMapper.insert(inStock);
            MaterialAuditRelationGenerateParam generateParam = new MaterialAuditRelationGenerateParam();
            generateParam.setType(MateriaAuditResourceTypeEnum.MATERIAL_IN_STOCK.getValue());
            generateParam.setStockId(inStock.getStockId());
            generateParam.setOrderId(inStock.getId());
            generateParam.setDeptId(inStock.getDeptId());


            MaterialAuditRelationGenerateResult generateResult = auditFlowRelationService.generateAuditFlowRelation(generateParam);
            AuditFlowRelation auditFlowRelation = generateResult.getAuditFlowRelation();
            inStock.setAuditStatus(auditFlowRelation.getAuditStatus());
            inStock.setAuditTime(auditFlowRelation.getAuditTime());
            inStock.setAuditUserId(auditFlowRelation.getAuditUserId());
            inStockMapper.updateById(inStock);
            // TODO 是否直接入库
        }else{
            flag = inStockMapper.updateById(inStock);
        }
        if(flag>0){
            return inStockMapper.selectById(inStock.getId());
        }else{
            return null;
        }
    }

    @Override
    public InStock getById(Long id) {
        return inStockMapper.selectById(id);
    }

    @Override
    public List<InStock> getByIds(List<Long> ids) {
        LambdaQueryWrapper<InStock> lqw = Wrappers.lambdaQuery();
        lqw.eq(InStock::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( InStock::getId, ids);
        return inStockMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        inStockMapper.deleteById(id);
        return true;
    }

    @Override
    public List<InStock> list(InStockForm form) {
        LambdaQueryWrapper<InStock> lqw = getQueryWrapper(form);
        return inStockMapper.selectList(lqw);
    }

    @Override
    public PageInfo<InStockVo> page(InStockForm form, PageQuery pageQuery) {
        Long userId = EmployeeHelper.getLoginEmployeeId();

        LambdaQueryWrapper<InStock> lqw = getQueryWrapper(form);
        Page<InStock> page = pageQuery.build();
        Page<InStock> result = inStockMapper.selectPage(page, lqw);
        PageInfo<InStockVo> tableDataInfo = PageInfo.build(result,InStockVo.class );
        List<InStockVo> list = tableDataInfo.getList();

        AuditFlowRelationCurrentUserQuery flowRelationCurrentUserQuery = new AuditFlowRelationCurrentUserQuery();
        flowRelationCurrentUserQuery.setType(MateriaAuditResourceTypeEnum.MATERIAL_IN_STOCK.getValue());
        flowRelationCurrentUserQuery.setStockId(form.getStockId());
        List<Long> beingOrderIds = auditFlowRelationService.listAuditBeingOrderIdsByCurrentUser(flowRelationCurrentUserQuery);
        for (InStockVo inStockVo : list) {
            if(beingOrderIds.contains(inStockVo.getId())){
                inStockVo.setHasAuditAuth(true);//当前登陆人有待审批
            }
            if (inStockVo.getInStockUserId().equals(userId)) {
                inStockVo.setHasInStockAuth(true);//当前登陆人有入库权限
            }
        }
        return tableDataInfo;
    }

    @Override
    public List<InStock> batchSave(List<InStock> auditFlowDetails) {
        inStockMapper.insertOrUpdateBatch(auditFlowDetails);
        List<Long> list = CollStreamUtil.toList(auditFlowDetails, InStock::getId);
        List<InStock> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<InStock> getQueryWrapper(InStockForm form) {

        LambdaQueryWrapper<InStock> lqw = Wrappers.lambdaQuery();
        lqw.eq(form.getStockId()!=null, InStock::getStockId, form.getStockId());
        lqw.eq(form.getStatus()!=null, InStock::getStatus, form.getStatus());
        lqw.eq(form.getAuditStatus()!=null, InStock::getAuditStatus, form.getAuditStatus());
        lqw.ge(form.getStartTime()!=null, InStock::getInStockTime, form.getStartTime());
        lqw.le(form.getEndTime()!=null, InStock::getInStockTime, form.getEndTime());
        lqw.eq( InStock::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }

}
