package com.clt.matlink.modules.base.common.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.exception.ServiceException;
import com.clt.matlink.common.security.LoginHelper;
import com.clt.matlink.modules.base.common.domain.entity.Material;
import com.clt.matlink.modules.base.common.domain.entity.StockDetail;
import com.clt.matlink.modules.base.common.domain.entity.StockRecord;
import com.clt.matlink.modules.base.common.domain.form.StockDetailForm;
import com.clt.matlink.modules.base.common.domain.form.StockSaveParam;
import com.clt.matlink.modules.base.common.mapper.StockDetailMapper;
import com.clt.matlink.modules.base.common.service.MaterialService;
import com.clt.matlink.modules.base.common.service.StockDetailService;
import com.clt.matlink.modules.base.common.service.StockRecordService;
import com.clt.matlink.modules.enums.*;
import com.clt.matlink.modules.instock.domain.entity.InStock;
import com.clt.matlink.modules.instock.domain.entity.InStockDetail;
import com.clt.matlink.modules.instock.domain.form.InStockDetailForm;
import com.clt.matlink.modules.instock.domain.form.InStockSaveParam;
import com.clt.matlink.modules.instock.service.InStockDetailService;
import com.clt.matlink.modules.instock.service.InStockService;
import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApply;
import com.clt.matlink.modules.outBoundApply.service.OutBoundApplyService;
import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import com.clt.matlink.modules.outstock.domain.entity.OutStockDetail;
import com.clt.matlink.modules.outstock.domain.form.OutStockDetailForm;
import com.clt.matlink.modules.outstock.domain.form.OutStockSaveParam;
import com.clt.matlink.modules.outstock.service.OutStockDetailService;
import com.clt.matlink.modules.outstock.service.OutStockService;
import com.clt.matlink.modules.purchase.domain.entity.Purchase;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseDetail;
import com.clt.matlink.modules.purchase.domain.form.PurchaseDetailForm;
import com.clt.matlink.modules.purchase.service.PurchaseDetailService;
import com.clt.matlink.modules.purchase.service.PurchaseService;
import com.clt.matlink.modules.task.domain.entity.Task;
import com.clt.matlink.modules.task.domain.entity.TaskDetail;
import com.clt.matlink.modules.task.domain.form.TaskDetailForm;
import com.clt.matlink.modules.task.service.TaskDetailService;
import com.clt.matlink.modules.task.service.TaskService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockDetailServiceImpl implements StockDetailService {
    @Autowired
    private StockDetailMapper stockDetailMapper;
    @Autowired
    private InStockDetailService inStockDetailService;
    @Autowired
    private InStockService inStockService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private StockRecordService stockRecordService;
    @Autowired
    private OutStockService outStockService;
    @Autowired
    private OutStockDetailService outStockDetailService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private OutBoundApplyService outBoundApplyService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskDetailService taskDetailService;
    @Autowired
    private PurchaseDetailService purchaseDetailService;

    @Override
    public List<StockDetail> save(StockSaveParam stockSaveParam) {
        Integer type = stockSaveParam.getType();
        Long orderId = stockSaveParam.getOrderId();
        if (type.equals(MateriaAuditResourceTypeEnum.MATERIAL_IN_STOCK.getValue())) {
            return handleInStock(orderId);
        } else if (type.equals(MateriaAuditResourceTypeEnum.MATERIAL_OUT_STOCK.getValue())) {
            return handleOutStock(orderId);
        } else if(type.equals(MateriaAuditResourceTypeEnum.MATERIAL_INVENTORY.getValue())){
            return handleTask(orderId);
        }else {
            throw new ServiceException("未知的审批资源类型");
        }
    }

    /**
     * 出库处理
     *
     * @param orderId
     * @return
     */
    private List<StockDetail> handleOutStock(Long orderId) {
        OutStock outStock = outStockService.getById(orderId);
        List<StockDetail> stockDetails = Lists.newArrayList();
        if (outStock.getAuditStatus() == MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus()) {
            OutStockDetailForm outStockDetailForm = new OutStockDetailForm();
            outStockDetailForm.setOutStockId(orderId);
            List<OutStockDetail> outStockDetails = outStockDetailService.list(outStockDetailForm);
            for (OutStockDetail outStockDetail : outStockDetails) {
                StockDetailForm stockDetailForm = new StockDetailForm();
                stockDetailForm.setStockId(outStockDetail.getStockId());
                stockDetailForm.setMaterialId(outStockDetail.getMaterialId());
                StockDetail stockDetail = this.getByConditions(stockDetailForm);
                if (stockDetail == null) {
                    throw new ServiceException(
                            StrUtil.format("出库物料不存在,stockId={},materialId={}",
                                    outStockDetail.getStockId(), outStockDetail.getMaterialId()));
                } else {
                    //采购退货出库，重新核算成本
                    if(outStock.getType().equals(2)){
                        // 新成本单价=（原成本单价*原库存数量-出库单价*出库数量）/ 剩余库存数量
                        BigDecimal inventoryAmount = stockDetail.getCostPrice().multiply(stockDetail.getCount()).subtract(outStockDetail.getPerPrice().multiply(outStockDetail.getActualCount()));
                        BigDecimal inventoryCount = stockDetail.getCount().subtract(outStockDetail.getActualCount());
                        stockDetail.setCostPrice(inventoryAmount.divide(inventoryCount,2, RoundingMode.HALF_UP));
                    }
                    //更新库存详情记录
                    BigDecimal count = stockDetail.getCount().subtract(outStockDetail.getActualCount());
                    if(count.compareTo(BigDecimal.ZERO)<0){
                        throw new ServiceException(
                                StrUtil.format("物料库存数量不足,stockId={},materialId={},出库数量={},库存数量={}",
                                        outStockDetail.getStockId(), outStockDetail.getMaterialId(),
                                        outStockDetail.getActualCount(), stockDetail.getCount()));
                    }
                    stockDetail.setCount(count);
                    stockDetail.setStockTime(new Date());
                    stockDetail.setTotalCostPrice(stockDetail.getCount().multiply(stockDetail.getCostPrice()));
                    stockDetailMapper.updateById(stockDetail);
                }
                //库存流水
                StockRecord stockRecord = new StockRecord();
                stockRecord.setType(MateriaAuditResourceTypeEnum.MATERIAL_OUT_STOCK.getValue());
                stockRecord.setRelatedOrderId(outStockDetail.getOutStockId());
                stockRecord.setMaterialId(outStockDetail.getMaterialId());
                stockRecord.setStockId(outStockDetail.getStockId());
                stockRecord.setQuantityChange(outStockDetail.getActualCount().negate());
                stockRecord.setBalanceAfter(stockDetail.getCount());
                stockRecord.setCostPrice(stockDetail.getCostPrice());
                stockRecord.setTotalCostPrice(stockDetail.getTotalCostPrice());
                stockRecord.setHandleUserId(LoginHelper.getLoginEmployeeId());
                stockRecord.setHandleTime(new Date());
                stockRecordService.save(stockRecord);
                stockDetails.add(stockDetail);
            }

            //修改库存状态审批状态 已出库
            outStock.setStatus(MateriaInOrOutStockStatusEnum.IN_OR_OUT_STOCK.getValue());
            OutStockSaveParam outStockSaveParam = BeanUtil.copyProperties(outStock, OutStockSaveParam.class);
            outStockService.save(outStockSaveParam);

            //领料状态修改为已出库
            if(outStock.getType().equals(1)){
                Long resourceId = outStock.getOriginOrderId();
                OutBoundApply outBoundApply = outBoundApplyService.getById(resourceId);
                outBoundApply.setStatus(MaterialOutBoundApplyStatusEnum.OUT_BOUND.getStatus());
                outBoundApplyService.save(outBoundApply);
            }
            //采购退货 修改采购单状态为已退货
            if(outStock.getType().equals(2)){
                Long resourceId = outStock.getOriginOrderId();
                Purchase oldPurchase = purchaseService.getById(resourceId);
                oldPurchase.setStatus(PurchasingStatusEnum.RETURNED.getStatus());
                purchaseService.save(oldPurchase);
            }
        }

        return stockDetails;
    }

    /**
     * 入库处理
     *
     * @param orderId
     * @return
     */
    private List<StockDetail> handleInStock(Long orderId) {
        InStock inStock = inStockService.getById(orderId);
        List<StockDetail> stockDetails = Lists.newArrayList();
        //入库单 审批通过
        if (inStock.getAuditStatus() == MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus()) {
            //查询入库单详情
            InStockDetailForm inStockDetailForm = new InStockDetailForm();
            inStockDetailForm.setInStockId(orderId);
            List<InStockDetail> inStockDetails = inStockDetailService.list(inStockDetailForm);
            for (InStockDetail inStockDetail : inStockDetails) {
                StockDetail stockDetail = handleInStockDetail(inStockDetail, inStock);
                //库存流水
                StockRecord stockRecord = new StockRecord();
                stockRecord.setType(MateriaAuditResourceTypeEnum.MATERIAL_IN_STOCK.getValue());
                stockRecord.setRelatedOrderId(inStockDetail.getInStockId());
                stockRecord.setMaterialId(inStockDetail.getMaterialId());
                stockRecord.setStockId(inStockDetail.getStockId());
                stockRecord.setQuantityChange(inStockDetail.getActualCount());
                stockRecord.setBalanceAfter(stockDetail.getCount());
                stockRecord.setCostPrice(stockDetail.getCostPrice());
                stockRecord.setTotalCostPrice(stockDetail.getTotalCostPrice());
                stockRecord.setHandleUserId(LoginHelper.getLoginEmployeeId());
                stockRecord.setHandleTime(new Date());
                stockRecordService.save(stockRecord);
                stockDetails.add(stockDetail);
            }


            //修改库存状态审批状态 已入库
            inStock.setStatus(MateriaInOrOutStockStatusEnum.IN_OR_OUT_STOCK.getValue());
            InStockSaveParam inStockSaveParam = BeanUtil.copyProperties(inStock, InStockSaveParam.class);
            inStockService.save(inStockSaveParam);

            //采购单
            if(inStock.getType().equals(2)){
                //采购状态修改为已入库
                Long resourceId = inStock.getOriginOrderId();
                Purchase oldPurchasing = purchaseService.getById(resourceId);
                oldPurchasing.setStatus(PurchasingStatusEnum.IN_STOCK.getStatus());
                purchaseService.save(oldPurchasing);
            }
            //领用归还
            if(inStock.getType().equals(3)){
                Long resourceId = inStock.getOriginOrderId();
                OutBoundApply oldOutBoundApply = outBoundApplyService.getById(resourceId);
                oldOutBoundApply.setStatus(MaterialOutBoundApplyStatusEnum.RETURNED.getStatus());
                outBoundApplyService.save(oldOutBoundApply);
            }
        }

        return stockDetails;
    }

    @Lock4j(keys = {"#inStockDetail.getStockId()", "#inStockDetail.getMaterialId()"})
    public StockDetail handleInStockDetail(InStockDetail inStockDetail, InStock inStock) {
        StockDetailForm stockDetailForm = new StockDetailForm();
        stockDetailForm.setMaterialId(inStockDetail.getMaterialId());
        stockDetailForm.setStockId(inStockDetail.getStockId());
        StockDetail stockDetail = this.getByConditions(stockDetailForm);
        Material material = materialService.getById(inStockDetail.getMaterialId());
        if (stockDetail == null) {
            //新增库存详情记录
            StockDetail newStockDetail = new StockDetail();
            newStockDetail.setStockId(inStockDetail.getStockId());
            newStockDetail.setMaterialTypeId(material.getMaterialTypeId());
            newStockDetail.setMaterialId(inStockDetail.getMaterialId());
            newStockDetail.setCount(inStockDetail.getActualCount());
            newStockDetail.setStockTime(new Date());
            newStockDetail.setCostPrice(inStockDetail.getPerPrice());
            newStockDetail.setTotalCostPrice(inStockDetail.getInStockPrice());
            stockDetailMapper.insert(newStockDetail);
            stockDetail = newStockDetail;
        } else {
            //更新库存详情记录，如果是采购入库，移动平均法核算成本单价
            if(inStock.getType().equals(2)){
                // 新成本单价=（原成本单价*原库存数量+入库单价*入库数量）/（原库存数量+入库数量）
                BigDecimal inventoryAmount =  stockDetail.getCostPrice().multiply(stockDetail.getCount()).add(inStockDetail.getPerPrice().multiply(inStockDetail.getActualCount()));
                BigDecimal inventoryCount = stockDetail.getCount().add(inStockDetail.getActualCount());
                stockDetail.setCostPrice(inventoryAmount.divide(inventoryCount, 2, RoundingMode.HALF_UP));
            }
            stockDetail.setCount(stockDetail.getCount().add(inStockDetail.getActualCount()));
            stockDetail.setStockTime(new Date());
            stockDetail.setTotalCostPrice(stockDetail.getCount().multiply(stockDetail.getCostPrice()));
            stockDetailMapper.updateById(stockDetail);
        }
        return stockDetail;
    }

    /**
     * 盘点处理
     *
     * @param orderId
     * @return
     */
    private List<StockDetail> handleTask(Long orderId) {
        Task task = taskService.getById(orderId);
        List<StockDetail> stockDetails = Lists.newArrayList();
        //审批通过
        if (task.getAuditStatus() == MateriaAuditStatusEnum.AUDIT_SUCCESS.getStatus()) {
            //获取盘点任务详情
            TaskDetailForm taskDetailForm = new TaskDetailForm();
            taskDetailForm.setTaskId(orderId);
            List<TaskDetail> taskDetails = taskDetailService.list(taskDetailForm);
            for (TaskDetail taskDetail : taskDetails) {
                StockDetailForm stockDetailForm = new StockDetailForm();
                stockDetailForm.setMaterialId(taskDetail.getMaterialId());
                stockDetailForm.setStockId(task.getStockId());
                StockDetail stockDetail = this.getByConditions(stockDetailForm);
                Material material = materialService.getById(taskDetail.getMaterialId());
                if (stockDetail == null) {
                    throw new ServiceException("盘点物料不存在");

                } else {
                    //更新库存详情记录
                    stockDetail.setCount(taskDetail.getRealCount());
                    stockDetail.setStockTime(new Date());
                    stockDetailMapper.updateById(stockDetail);
                }
                //库存流水
                StockRecord stockRecord = new StockRecord();
                stockRecord.setType(MateriaAuditResourceTypeEnum.MATERIAL_INVENTORY.getValue());
                stockRecord.setRelatedOrderId(taskDetail.getTaskId());
                stockRecord.setMaterialId(taskDetail.getMaterialId());
                stockRecord.setStockId(task.getStockId());
                stockRecord.setQuantityChange(taskDetail.getDiffCount());
                stockRecord.setBalanceAfter(stockDetail.getCount());
                stockRecord.setCostPrice(stockDetail.getCostPrice());
                stockRecord.setTotalCostPrice(stockDetail.getTotalCostPrice());
                stockRecord.setHandleUserId(LoginHelper.getLoginEmployeeId());
                stockRecord.setHandleTime(new Date());
                stockRecordService.save(stockRecord);
                stockDetails.add(stockDetail);
            }
        }
        return stockDetails;
    }
    @Override
    public StockDetail getById(Long id) {
        return stockDetailMapper.selectById(id);
    }

    @Override
    public StockDetail getByConditions(StockDetailForm stockDetailForm) {
        LambdaQueryWrapper<StockDetail> lqw = getQueryWrapper(stockDetailForm);
        return stockDetailMapper.selectOne(lqw);
    }

    @Override
    public List<StockDetail> getByIds(List<Long> ids) {
        LambdaQueryWrapper<StockDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(StockDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in(StockDetail::getId, ids);
        return stockDetailMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        stockDetailMapper.deleteById(id);
        return true;
    }

    @Override
    public List<StockDetail> list(StockDetailForm stockDetailForm) {
        LambdaQueryWrapper<StockDetail> lqw = getQueryWrapper(stockDetailForm);
        return stockDetailMapper.selectList(lqw);
    }

    @Override
    public PageInfo<StockDetail> page(StockDetailForm stockDetailForm, PageQuery pageQuery) {
        LambdaQueryWrapper<StockDetail> lqw = getQueryWrapper(stockDetailForm);
        Page<StockDetail> page = pageQuery.build();
        Page<StockDetail> result = stockDetailMapper.selectPage(page, lqw);
        PageInfo<StockDetail> tableDataInfo = PageInfo.build(result, StockDetail.class);
        return tableDataInfo;
    }

    private LambdaQueryWrapper<StockDetail> getQueryWrapper(StockDetailForm stockDetailForm) {
        LambdaQueryWrapper<StockDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(stockDetailForm.getStockId() != null, StockDetail::getStockId, stockDetailForm.getStockId());
        lqw.eq(stockDetailForm.getMaterialId() != null, StockDetail::getMaterialId, stockDetailForm.getMaterialId());
        lqw.eq(stockDetailForm.getMaterialTypeId() != null, StockDetail::getMaterialTypeId, stockDetailForm.getMaterialTypeId());
        lqw.in(CollUtil.isNotEmpty(stockDetailForm.getMaterialIds()) , StockDetail::getMaterialId, stockDetailForm.getMaterialIds());
        lqw.eq(StockDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
