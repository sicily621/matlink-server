package com.clt.matlink.modules.outBoundApply.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApplyDetail;
import com.clt.matlink.modules.outBoundApply.domain.form.OutBoundApplyDetailForm;
import com.clt.matlink.modules.outBoundApply.service.OutBoundApplyDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 物料领用详情
 */
@RequestMapping("/outBoundApply/detail")
@RestController
public class OutBoundApplyDetailController {
    @Autowired
    private OutBoundApplyDetailService outBoundApplyDetailService;
    /**
     * 批量修改物料领用详情
     * @param outBoundApplyDetails
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<OutBoundApplyDetail>> batchUpdate(@RequestBody List<OutBoundApplyDetail> outBoundApplyDetails){
        return Result.success(outBoundApplyDetailService.batchSave(outBoundApplyDetails));
    }

    /**
     * 删除物料领用详情
     */
    @DeleteMapping("{billId}")
    public Result<Void> deleteByApplyId(@PathVariable("billId") Long billId){
        outBoundApplyDetailService.deleteByApplyId(billId);
        return Result.success();
    }
    /**
     * 查询物料领用详情列表
     */
    @GetMapping("/list")
    public Result<List<OutBoundApplyDetail>> list(OutBoundApplyDetailForm outBoundApplyDetailForm){
        return Result.success(outBoundApplyDetailService.list(outBoundApplyDetailForm));
    }
}
