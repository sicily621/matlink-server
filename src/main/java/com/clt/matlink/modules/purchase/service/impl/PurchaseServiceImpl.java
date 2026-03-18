package com.clt.matlink.modules.purchase.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.common.security.LoginHelper;
import com.clt.matlink.modules.enums.MateriaAuditStatusEnum;
import com.clt.matlink.modules.flow.domain.entity.AuditFlowRelation;
import com.clt.matlink.modules.enums.MateriaAuditResourceTypeEnum;
import com.clt.matlink.modules.flow.domain.form.AuditFlowRelationCurrentUserQuery;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationGenerateParam;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationGenerateResult;
import com.clt.matlink.modules.flow.domain.vo.MaterialAuditRelationResult;
import com.clt.matlink.modules.flow.service.AuditFlowRelationService;
import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import com.clt.matlink.modules.outstock.domain.form.OutStockSaveParam;
import com.clt.matlink.modules.purchase.domain.entity.Purchase;
import com.clt.matlink.modules.purchase.domain.form.PurchaseForm;
import com.clt.matlink.modules.purchase.domain.form.PurchaseSaveParam;
import com.clt.matlink.modules.purchase.domain.vo.PurchaseVo;
import com.clt.matlink.modules.purchase.mapper.PurchaseMapper;
import com.clt.matlink.modules.purchase.service.PurchaseService;
import com.clt.matlink.modules.system.employee.domain.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    private PurchaseMapper purchaseMapper;
    @Autowired
    private AuditFlowRelationService auditFlowRelationService;
    @Override
    public Purchase save(Purchase purchase) {
        int flag = 0;
        if(purchase.getId()==null){
            //保存采购单
            flag= purchaseMapper.insert(purchase);

            //生成审批单和审批记录
            MaterialAuditRelationGenerateParam generateParam = new MaterialAuditRelationGenerateParam();
            generateParam.setType(MateriaAuditResourceTypeEnum.PURCHASING.getValue());
            generateParam.setStockId(purchase.getStockId());
            generateParam.setOrderId(purchase.getId());
            generateParam.setDeptId(purchase.getDeptId());
            MaterialAuditRelationGenerateResult generateResult = auditFlowRelationService.generateAuditFlowRelation(generateParam);
            AuditFlowRelation auditFlowRelation = generateResult.getAuditFlowRelation();

            //设置采购单的审批状态
            purchase.setAuditStatus(auditFlowRelation.getAuditStatus());
            purchase.setAuditTime(auditFlowRelation.getAuditTime());
            purchase.setAuditUserId(auditFlowRelation.getAuditUserId());
            purchaseMapper.updateById(purchase);
        }else{
            flag = purchaseMapper.updateById(purchase);
        }
        if(flag>0){
            return purchaseMapper.selectById(purchase.getId());
        }else{
            return null;
        }
    }

    @Override
    public Purchase getById(Long id) {
        return purchaseMapper.selectById(id);
    }

    @Override
    public List<Purchase> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Purchase> lqw = Wrappers.lambdaQuery();
        lqw.eq(Purchase::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Purchase::getId, ids);
        return purchaseMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        purchaseMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Purchase> list(PurchaseForm form) {
        LambdaQueryWrapper<Purchase> lqw = getQueryWrapper(form);
        return purchaseMapper.selectList(lqw);
    }

    @Override
    public PageInfo<PurchaseVo> page(PurchaseForm form, PageQuery pageQuery) {
        Long userId = LoginHelper.getLoginEmployeeId();

        LambdaQueryWrapper<Purchase> lqw = getQueryWrapper(form);
        Page<Purchase> page = pageQuery.build();
        Page<Purchase> result = purchaseMapper.selectPage(page, lqw);
        PageInfo<PurchaseVo> tableDataInfo = PageInfo.build(result,PurchaseVo.class );
        List<PurchaseVo> list = tableDataInfo.getList();

        AuditFlowRelationCurrentUserQuery flowRelationCurrentUserQuery = new AuditFlowRelationCurrentUserQuery();
        flowRelationCurrentUserQuery.setType(MateriaAuditResourceTypeEnum.PURCHASING.getValue());
        flowRelationCurrentUserQuery.setStockId(form.getStockId());
        //当前用户能审批的关联单Id列表
        List<Long> beingOrderIds = auditFlowRelationService.listAuditBeingOrderIdsByCurrentUser(flowRelationCurrentUserQuery);
        for (PurchaseVo purchaseVo : list) {
            if(beingOrderIds.contains(purchaseVo.getId())){
                purchaseVo.setHasAuditAuth(true);//当前登陆人有待审批
            }

        }
        return tableDataInfo;
    }

    @Override
    public List<Purchase> batchSave(List<Purchase> auditFlowDetails) {
        purchaseMapper.insertOrUpdateBatch(auditFlowDetails);
        List<Long> list = CollStreamUtil.toList(auditFlowDetails, Purchase::getId);
        List<Purchase> result = getByIds(list);
        return result;
    }
    @Override
    public Purchase updateAuditStatus(MaterialAuditRelationParam generateParam) {
        Purchase old = this.getById(generateParam.getOrderId());
        if (old == null) {
            throw new ServiceException("采购单不存在");
        }
        if (old.getStatus() != null &&  old.getStatus() == 2){
            throw new ServiceException("采购单已作废");
        }
        if (old.getStatus() != null &&  old.getStatus() == 1){
            throw new ServiceException("采购单已出库");
        }

        //处理审批
        MaterialAuditRelationResult auditResult = auditFlowRelationService.processAuditFlowRelation(generateParam);
        AuditFlowRelation flowRelation = auditResult.getFlowRelation();
        // 更新审批状态
        PurchaseSaveParam entity = new PurchaseSaveParam();
        entity.setId(flowRelation.getOrderId());
        entity.setAuditStatus(flowRelation.getAuditStatus());
        entity.setAuditTime(flowRelation.getAuditTime());
        entity.setAuditUserId(generateParam.getCurrentUserId());
        this.save(entity);
        return this.getById(old.getId());
    }

    private LambdaQueryWrapper<Purchase> getQueryWrapper(PurchaseForm form) {

        LambdaQueryWrapper<Purchase> lqw = Wrappers.lambdaQuery();
        lqw.eq(form.getStockId()!=null, Purchase::getStockId, form.getStockId());
        lqw.eq(form.getStatus()!=null, Purchase::getStatus, form.getStatus());
        lqw.eq(form.getAuditStatus()!=null, Purchase::getAuditStatus, form.getAuditStatus());
        lqw.ge(form.getStartTime()!=null, Purchase::getApplyDate, form.getStartTime());
        lqw.le(form.getEndTime()!=null, Purchase::getApplyDate, form.getEndTime());
        lqw.like(form.getBillNo()!=null, Purchase::getBillNo, form.getBillNo());
        lqw.eq( Purchase::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
    @Override
    public Boolean validateBillNo(Purchase purchase) {
        if (purchase.getBillNo() == null || purchase.getBillNo().trim().isEmpty()) {
            return false;
        }
        LambdaQueryWrapper<Purchase> lqw = Wrappers.lambdaQuery();
        lqw.eq(Purchase::getBillNo, purchase.getBillNo());
        lqw.eq(Purchase::getDelFlag, DelFlagEnum.NORMAL.getValue());
        if (purchase.getId() != null) {
            // 【编辑场景】：排除当前这条记录本身
            // 逻辑：查找 (code相同 AND 未删除 AND id != 当前id) 的记录
            lqw.ne(Purchase::getId, purchase.getId());
        }
        long count = purchaseMapper.selectCount(lqw);
        return count == 0;
    }
}
