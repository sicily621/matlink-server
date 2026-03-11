package com.clt.matlink.modules.instock.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.common.domain.vo.MaterialVO;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.instock.domain.entity.InStock;
import com.clt.matlink.modules.instock.domain.form.InStockForm;
import com.clt.matlink.modules.instock.domain.form.InStockSaveParam;
import com.clt.matlink.modules.instock.domain.vo.InStockVo;
import com.clt.matlink.modules.instock.service.InStockService;
import com.clt.matlink.modules.purchase.domain.entity.Purchase;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 入库
 */
@RequestMapping("/inStock")
@RestController
public class InStockController {
    @Autowired
    private InStockService inStockService;
    /**
     * 新建入库
     */
    @PostMapping()
    public Result<InStock> create(@RequestBody InStockSaveParam inStock){
        return Result.success(inStockService.save(inStock));
    }
    /**
     * 修改入库
     * @param inStock
     * @return
     */
    @PutMapping()
    public Result<InStock> update(@RequestBody InStockSaveParam inStock){
        return Result.success(inStockService.save(inStock));
    }
    /**
     * 批量修改入库
     * @param materials
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<InStock>> batchUpdate(@RequestBody List<InStock> materials){
        return Result.success(inStockService.batchSave(materials));
    }
    /**
     * 根据入库Id查询入库
     */
    @GetMapping("{id}")
    public Result<InStock> getById(@PathVariable("id") Long id){
        return Result.success(inStockService.getById(id));
    }
    /**
     * 根据入库Ids查询入库列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<InStock>> getByIds(@PathVariable("ids") List<Long> ids){
        return Result.success(inStockService.getByIds(ids));
    }

    /**
     * 删除入库
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        inStockService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询入库列表
     */
    @GetMapping("/list")
    public Result<List<InStock>> list(InStockForm inStockForm){
        return Result.success(inStockService.list(inStockForm));
    }

    /**
     * 分页查询入库列表
     */
    @GetMapping("/page")
    public Result<PageInfo<InStockVo>> page(InStockForm inStockForm, PageQuery pageQuery){
        return Result.success(inStockService.page(inStockForm, pageQuery));
    }

    /**
     * 入库审批
     */
    @PostMapping("/updateAuditStatus")
    public Result<InStock> updateAuditStatus(
            @RequestBody
            MaterialAuditRelationParam generateParam) {
        return Result.success(inStockService.updateAuditStatus(generateParam));
    }
    /**
     * 校验单号是否重复
     */
    @PostMapping("/validateInStockNo")
    public Result<Boolean> validateInStockNo(@RequestBody InStock inStock){
        return Result.success(inStockService.validateInStockNo(inStock));
    }
}
