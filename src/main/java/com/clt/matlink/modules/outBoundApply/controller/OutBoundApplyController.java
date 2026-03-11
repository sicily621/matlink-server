package com.clt.matlink.modules.outBoundApply.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApply;
import com.clt.matlink.modules.outBoundApply.domain.form.OutBoundApplyForm;
import com.clt.matlink.modules.outBoundApply.domain.vo.OutBoundApplyVo;
import com.clt.matlink.modules.outBoundApply.service.OutBoundApplyService;
import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import com.clt.matlink.modules.purchase.domain.entity.Purchase;
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
 * 物料领用
 */
@RequestMapping("/outBoundApply")
@RestController
public class OutBoundApplyController {
    @Autowired
    private OutBoundApplyService outBoundApplyService;
    /**
     * 新建领用
     */
    @PostMapping()
    public Result<OutBoundApply> create(@RequestBody
                                   OutBoundApply outBoundApply){
        return Result.success(outBoundApplyService.save(outBoundApply));
    }
    /**
     * 修改领用
     * @param outBoundApply
     * @return
     */
    @PutMapping()
    public Result<OutBoundApply> update(@RequestBody OutBoundApply outBoundApply){
        return Result.success(outBoundApplyService.save(outBoundApply));
    }
    /**
     * 批量修改领用
     * @param materials
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<OutBoundApply>> batchUpdate(@RequestBody List<OutBoundApply> materials){
        return Result.success(outBoundApplyService.batchSave(materials));
    }
    /**
     * 根据领用Id查询领用
     */
    @GetMapping("{id}")
    public Result<OutBoundApply> getById(@PathVariable("id") Long id){
        return Result.success(outBoundApplyService.getById(id));
    }
    /**
     * 根据领用Ids查询领用列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<OutBoundApply>> getByIds(@PathVariable("ids") List<Long> ids){
        return Result.success(outBoundApplyService.getByIds(ids));
    }

    /**
     * 删除领用
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        outBoundApplyService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询领用列表
     */
    @GetMapping("/list")
    public Result<List<OutBoundApply>> list(OutBoundApplyForm inStockForm){
        return Result.success(outBoundApplyService.list(inStockForm));
    }

    /**
     * 分页查询领用列表
     */
    @GetMapping("/page")
    public Result<PageInfo<OutBoundApplyVo>> page(OutBoundApplyForm inStockForm, PageQuery pageQuery){
        return Result.success(outBoundApplyService.page(inStockForm, pageQuery));
    }
    /**
     * 领用审批
     */
    @PostMapping("/updateAuditStatus")
    public Result<OutBoundApply> updateAuditStatus(
            @RequestBody
            MaterialAuditRelationParam generateParam) {
        return Result.success(outBoundApplyService.updateAuditStatus(generateParam));
    }
    /**
     * 校验单号是否重复
     */
    @PostMapping("/validateApplyNo")
    public Result<Boolean> validateApplyNo(@RequestBody OutBoundApply outBoundApply){
        return Result.success(outBoundApplyService.validateApplyNo(outBoundApply));
    }
}
