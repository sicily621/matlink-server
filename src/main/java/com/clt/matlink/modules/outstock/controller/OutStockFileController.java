package com.clt.matlink.modules.outstock.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.outstock.domain.entity.OutStockFile;
import com.clt.matlink.modules.outstock.domain.form.OutStockFileForm;
import com.clt.matlink.modules.outstock.service.OutStockFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 出库文件
 */
@RequestMapping("/outStock/file")
@RestController
public class OutStockFileController {
    @Autowired
    private OutStockFileService outStockFileService;
    /**
     * 新建出库文件
     */
    @PostMapping()
    public Result<OutStockFile> create(@RequestBody OutStockFile outStockFile){
        return Result.success(outStockFileService.save(outStockFile));
    }
    /**
     * 修改出库文件
     * @param outStockFile
     * @return
     */
    @PutMapping()
    public Result<OutStockFile> update(@RequestBody OutStockFile outStockFile){
        return Result.success(outStockFileService.save(outStockFile));
    }
    /**
     * 批量修改出库文件
     * @param outStockFiles
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<OutStockFile>> batchUpdate(@RequestBody List<OutStockFile> outStockFiles){
        return Result.success(outStockFileService.batchSave(outStockFiles));
    }
    /**
     * 根据出库文件Id查询出库文件
     */
    @GetMapping("{id}")
    public Result<OutStockFile> getById(@PathVariable("id") Long id){
        return Result.success(outStockFileService.getById(id));
    }
    /**
     * 根据出库文件Ids查询出库文件列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<OutStockFile>> getByIds(@PathVariable("ids") List<Long> ids){
        return Result.success(outStockFileService.getByIds(ids));
    }

    /**
     * 删除出库文件
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        outStockFileService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询出库文件列表
     */
    @GetMapping("/list")
    public Result<List<OutStockFile>> list(OutStockFileForm outStockFileForm){
        return Result.success(outStockFileService.list(outStockFileForm));
    }

    /**
     * 分页查询出库文件列表
     */
    @GetMapping("/page")
    public Result<PageInfo<OutStockFile>> page(OutStockFileForm outStockFileForm, PageQuery pageQuery){
        return Result.success(outStockFileService.page(outStockFileForm, pageQuery));
    }

}
