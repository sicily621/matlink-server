package com.clt.matlink.modules.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.inventory.domain.entity.Inventory;
import com.clt.matlink.modules.inventory.domain.form.InventoryForm;
import com.clt.matlink.modules.inventory.mapper.InventoryMapper;
import com.clt.matlink.modules.inventory.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryMapper inventoryMapper;
    @Override
    public Inventory save(Inventory inventory) {
        int flag = 0;
        if(inventory.getId()==null){
            flag= inventoryMapper.insert(inventory);
        }else{
            flag = inventoryMapper.updateById(inventory);
        }
        if(flag>0){
            return inventoryMapper.selectById(inventory.getId());
        }else{
            return null;
        }
    }

    @Override
    public Inventory getById(Long id) {
        return inventoryMapper.selectById(id);
    }

    @Override
    public List<Inventory> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Inventory> lqw = Wrappers.lambdaQuery();
        lqw.eq(Inventory::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Inventory::getId, ids);
        return inventoryMapper.selectList(lqw);
    }

    @Override
    public boolean deleteById(Long id) {
        inventoryMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Inventory> list(InventoryForm inventoryForm) {
        LambdaQueryWrapper<Inventory> lqw = getQueryWrapper(inventoryForm);
        return inventoryMapper.selectList(lqw);
    }

    @Override
    public PageInfo<Inventory> page(InventoryForm inventoryForm, PageQuery pageQuery) {
        LambdaQueryWrapper<Inventory> lqw = getQueryWrapper(inventoryForm);
        Page<Inventory> page = pageQuery.build();
        Page<Inventory> result = inventoryMapper.selectPage(page, lqw);
        PageInfo<Inventory> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<Inventory> receipt(List<Inventory> inventoryList) {
        // 校验输入列表
        if (inventoryList == null || inventoryList.isEmpty()) {
            return new ArrayList<>(); // 返回空列表而非null，避免NPE
        }

        List<Inventory> resultList = new ArrayList<>(inventoryList.size());

        for (Inventory inventory : inventoryList) {
            // 跳过空对象
            if (inventory == null) {
                continue;
            }

            // 构建查询条件
            LambdaQueryWrapper<Inventory> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(inventory.getProductId() != null, Inventory::getProductId, inventory.getProductId())
                        .eq(inventory.getWarehouseId() != null, Inventory::getWarehouseId, inventory.getWarehouseId())
                        .eq(inventory.getShelfId() != null, Inventory::getShelfId, inventory.getShelfId())
                        .eq(inventory.getAreaId() != null, Inventory::getAreaId, inventory.getAreaId())
                        .eq(Inventory::getDelFlag, DelFlagEnum.NORMAL.getValue());

            // 查询是否存在匹配记录
            Inventory existingInventory = inventoryMapper.selectOne(queryWrapper);
            BigDecimal incomingQty = inventory.getQuantity() != null ? inventory.getQuantity() : BigDecimal.ZERO;

            if (existingInventory != null) {
                // 存在匹配记录，累加数量
                BigDecimal existingQty = existingInventory.getQuantity() != null ? existingInventory.getQuantity() : BigDecimal.ZERO;
                BigDecimal newQuantity = existingQty.add(incomingQty); // 累加操作
                existingInventory.setQuantity(newQuantity);

                // 更新并添加到结果列表
                inventoryMapper.updateById(existingInventory);
                resultList.add(existingInventory);
            } else {
                // 不存在匹配记录，新增
                inventoryMapper.insert(inventory);
                resultList.add(inventory);
            }
        }

        return resultList; // 返回完整处理结果列表
    }

    @Override
    public List<Inventory> shipment(List<Inventory> inventoryList) {
        // 校验输入列表
        if (inventoryList == null || inventoryList.isEmpty()) {
            return new ArrayList<>(); // 返回空列表而非null，避免NPE
        }

        List<Inventory> resultList = new ArrayList<>(inventoryList.size());

        for (Inventory inventory : inventoryList) {
            // 跳过空对象
            if (inventory == null) {
                continue;
            }

            // 构建查询条件
            LambdaQueryWrapper<Inventory> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(inventory.getProductId() != null, Inventory::getProductId, inventory.getProductId())
                        .eq(inventory.getWarehouseId() != null, Inventory::getWarehouseId, inventory.getWarehouseId())
                        .eq(inventory.getShelfId() != null, Inventory::getShelfId, inventory.getShelfId())
                        .eq(inventory.getAreaId() != null, Inventory::getAreaId, inventory.getAreaId())
                        .eq(Inventory::getDelFlag, DelFlagEnum.NORMAL.getValue());

            // 查询是否存在匹配记录
            Inventory existingInventory = inventoryMapper.selectOne(queryWrapper);
            BigDecimal incomingQty = inventory.getQuantity() != null ? inventory.getQuantity() : BigDecimal.ZERO;

            if (existingInventory != null) {
                // 存在匹配记录，累加数量
                BigDecimal existingQty = existingInventory.getQuantity() != null ? existingInventory.getQuantity() : BigDecimal.ZERO;
                BigDecimal newQuantity = existingQty.subtract(incomingQty); // 累加操作
                existingInventory.setQuantity(newQuantity);

                // 更新并添加到结果列表
                inventoryMapper.updateById(existingInventory);
                resultList.add(existingInventory);
            } else {
                // 不存在匹配记录，新增
                inventoryMapper.insert(inventory);
                resultList.add(inventory);
            }
        }

        return resultList; // 返回完整处理结果列表
    }

    @Override
    public List<Inventory> batchUpdate(List<Inventory> inventoryList) {
        // 校验输入列表
        if (inventoryList == null || inventoryList.isEmpty()) {
            return new ArrayList<>(); // 返回空列表而非null，避免NPE
        }

        List<Inventory> resultList = new ArrayList<>(inventoryList.size());

        for (Inventory inventory : inventoryList) {
            // 跳过空对象
            if (inventory == null) {
                continue;
            }

            // 构建查询条件
            LambdaQueryWrapper<Inventory> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(inventory.getProductId() != null, Inventory::getProductId, inventory.getProductId())
                        .eq(inventory.getWarehouseId() != null, Inventory::getWarehouseId, inventory.getWarehouseId())
                        .eq(inventory.getShelfId() != null, Inventory::getShelfId, inventory.getShelfId())
                        .eq(inventory.getAreaId() != null, Inventory::getAreaId, inventory.getAreaId())
                        .eq(Inventory::getDelFlag, DelFlagEnum.NORMAL.getValue());

            // 查询是否存在匹配记录
            Inventory existingInventory = inventoryMapper.selectOne(queryWrapper);
            BigDecimal incomingQty = inventory.getQuantity() != null ? inventory.getQuantity() : BigDecimal.ZERO;

            if (existingInventory != null) {
                // 存在匹配记录，累加数量
//                BigDecimal existingQty = existingInventory.getQuantity() != null ? existingInventory.getQuantity() : BigDecimal.ZERO;
//                BigDecimal newQuantity = existingQty.subtract(incomingQty); // 累加操作
                existingInventory.setQuantity(incomingQty);

                // 更新并添加到结果列表
                inventoryMapper.updateById(existingInventory);
                resultList.add(existingInventory);
            } else {
                // 不存在匹配记录，新增
                inventoryMapper.insert(inventory);
                resultList.add(inventory);
            }
        }

        return resultList; // 返回完整处理结果列表
    }

    @Override
    public List<Inventory> getByProductIds(List<Long> ids) {
        LambdaQueryWrapper<Inventory> lqw = Wrappers.lambdaQuery();
        lqw.eq(Inventory::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Inventory::getProductId, ids);
        return inventoryMapper.selectList(lqw);
    }

    private LambdaQueryWrapper<Inventory> getQueryWrapper(InventoryForm inventoryForm) {
        LambdaQueryWrapper<Inventory> lqw = Wrappers.lambdaQuery();
        lqw.eq(inventoryForm.getProductId()!=null, Inventory::getProductId, inventoryForm.getProductId());
        lqw.eq(inventoryForm.getWarehouseId()!=null, Inventory::getWarehouseId, inventoryForm.getWarehouseId());
        lqw.eq(inventoryForm.getShelfId()!=null, Inventory::getShelfId, inventoryForm.getShelfId());
        lqw.eq(inventoryForm.getAreaId()!=null, Inventory::getAreaId, inventoryForm.getAreaId());
        lqw.gt( Inventory::getQuantity, 0);
        lqw.eq( Inventory::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
    
}
