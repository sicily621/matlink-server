package com.clt.matlink.modules.purchase.controller;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.purchase.domain.entity.PurchaseDemand;
import com.clt.matlink.modules.purchase.domain.form.PurchaseDemandForm;
import com.clt.matlink.modules.purchase.service.PurchaseDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 采购需求
 */
@RequestMapping("/purchase/demand")
@RestController
public class PurchaseDemandController {
    @Autowired
    private PurchaseDemandService purchaseDemandService;
    /**
     * 新增采购需求
     */
    @PostMapping()
    public Result<PurchaseDemand> create(@RequestBody PurchaseDemand purchaseDemand){
        return Result.success(purchaseDemandService.save(purchaseDemand));
    }

    /**
     * 修改采购需求
     * @param purchaseDemand
     * @return
     */
    @PutMapping()
    public Result<PurchaseDemand> update(@RequestBody PurchaseDemand purchaseDemand){
        return Result.success(purchaseDemandService.save(purchaseDemand));
    }
    /**
     * 根据采购需求Id查询采购需求
     */
    @GetMapping("{id}")
    public Result<PurchaseDemand> getById(@PathVariable("id") Long id){
        return Result.success(purchaseDemandService.getById(id));
    }
    /**
     * 删除采购需求
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        purchaseDemandService.deleteById(id);
        return Result.success();
    }
    /**
     * 分页查询采购需求列表
     */
    @GetMapping("/page")
    public Result<PageInfo<PurchaseDemand>> page(PurchaseDemandForm purchaseDemandForm){
        return Result.success(purchaseDemandService.page(purchaseDemandForm));
    }
    /**
     * 查询采购需求列表
     */
    @GetMapping("/list")
    public Result<List<PurchaseDemand>> list(){
        return Result.success(purchaseDemandService.list());
    }

}
