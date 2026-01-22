package com.clt.matlink.modules.purchase.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.purchase.domain.entity.Purchase;
import com.clt.matlink.modules.purchase.domain.form.PurchaseForm;
import com.clt.matlink.modules.purchase.domain.vo.PurchaseVo;
import com.clt.matlink.modules.purchase.service.PurchaseService;
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
 * 采购
 */
@RequestMapping("/purchase")
@RestController
public class PurchaseController {
    @Autowired
    private PurchaseService purchaseService;
    /**
     * 新建采购
     */
    @PostMapping()
    public Result<Purchase> create(@RequestBody
                                  Purchase purchase){
        return Result.success(purchaseService.save(purchase));
    }
    /**
     * 修改采购
     * @param purchase
     * @return
     */
    @PutMapping()
    public Result<Purchase> update(@RequestBody Purchase purchase){
        return Result.success(purchaseService.save(purchase));
    }
    /**
     * 批量修改采购
     * @param materials
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<Purchase>> batchUpdate(@RequestBody List<Purchase> materials){
        return Result.success(purchaseService.batchSave(materials));
    }
    /**
     * 根据采购Id查询采购
     */
    @GetMapping("{id}")
    public Result<Purchase> getById(@PathVariable("id") Long id){
        return Result.success(purchaseService.getById(id));
    }
    /**
     * 根据采购Ids查询采购列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Purchase>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(purchaseService.getByIds(ids));
    }

    /**
     * 删除采购
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        purchaseService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询采购列表
     */
    @GetMapping("/list")
    public Result<List<Purchase>> list(PurchaseForm inStockForm){
        return Result.success(purchaseService.list(inStockForm));
    }

    /**
     * 分页查询采购列表
     */
    @GetMapping("/page")
    public Result<PageInfo<PurchaseVo>> page(PurchaseForm inStockForm, PageQuery pageQuery){
        return Result.success(purchaseService.page(inStockForm, pageQuery));
    }
}
