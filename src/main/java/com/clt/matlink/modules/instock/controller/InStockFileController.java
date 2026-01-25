package com.clt.matlink.modules.instock.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.instock.domain.entity.InStockFile;
import com.clt.matlink.modules.instock.domain.form.InStockFileForm;
import com.clt.matlink.modules.instock.service.InStockFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 入库文件
 */
@RequestMapping("/inStock/file")
@RestController
public class InStockFileController {
    @Autowired
    private InStockFileService inStockFileService;
    /**
     * 新建入库文件
     */
    @PostMapping()
    public Result<InStockFile> create(@RequestBody InStockFile inStockFile){
        return Result.success(inStockFileService.save(inStockFile));
    }
    /**
     * 修改入库文件
     * @param inStockFile
     * @return
     */
    @PutMapping()
    public Result<InStockFile> update(@RequestBody InStockFile inStockFile){
        return Result.success(inStockFileService.save(inStockFile));
    }
    /**
     * 批量修改入库文件
     * @param inStockFiles
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<InStockFile>> batchUpdate(@RequestBody List<InStockFile> inStockFiles){
        return Result.success(inStockFileService.batchSave(inStockFiles));
    }
    /**
     * 根据入库文件Id查询入库文件
     */
    @GetMapping("{id}")
    public Result<InStockFile> getById(@PathVariable("id") Long id){
        return Result.success(inStockFileService.getById(id));
    }
    /**
     * 根据入库文件Ids查询入库文件列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<InStockFile>> getByIds(@PathVariable("ids") List<Long> ids){
        return Result.success(inStockFileService.getByIds(ids));
    }

    /**
     * 删除入库文件
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        inStockFileService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询入库文件列表
     */
    @GetMapping("/list")
    public Result<List<InStockFile>> list(InStockFileForm inStockFileForm){
        return Result.success(inStockFileService.list(inStockFileForm));
    }

    /**
     * 分页查询入库文件列表
     */
    @GetMapping("/page")
    public Result<PageInfo<InStockFile>> page(InStockFileForm inStockFileForm, PageQuery pageQuery){
        return Result.success(inStockFileService.page(inStockFileForm, pageQuery));
    }

}
