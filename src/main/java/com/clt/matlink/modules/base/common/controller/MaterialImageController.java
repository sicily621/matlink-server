package com.clt.matlink.modules.base.common.controller;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.common.domain.entity.MaterialImage;
import com.clt.matlink.modules.base.common.domain.form.MaterialImageForm;
import com.clt.matlink.modules.base.common.service.MaterialImageService;
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
 * 物料图片
 */
@RequestMapping("/material/image")
@RestController
public class MaterialImageController {
    @Autowired
    private MaterialImageService materialImageService;
    /**
     * 新建物料图片
     */
    @PostMapping()
    public Result<MaterialImage> create(@RequestBody MaterialImage materialImage){
        return Result.success(materialImageService.save(materialImage));
    }
    /**
     * 修改物料图片
     * @param materialImage
     * @return
     */
    @PutMapping()
    public Result<MaterialImage> update(@RequestBody MaterialImage materialImage){
        return Result.success(materialImageService.save(materialImage));
    }
    /**
     * 批量修改物料图片
     * @param materialImages
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<MaterialImage>> batchUpdate(@RequestBody List<MaterialImage> materialImages){
        return Result.success(materialImageService.batchSave(materialImages));
    }
    /**
     * 根据物料图片Id查询物料图片
     */
    @GetMapping("{id}")
    public Result<MaterialImage> getById(@PathVariable("id") Long id){
        return Result.success(materialImageService.getById(id));
    }
    /**
     * 根据物料图片Ids查询物料图片列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<MaterialImage>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(materialImageService.getByIds(ids));
    }

    /**
     * 删除物料图片
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        materialImageService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询物料图片列表
     */
    @GetMapping("/list")
    public Result<List<MaterialImage>> list(MaterialImageForm materialImageForm){
        return Result.success(materialImageService.list(materialImageForm));
    }

    /**
     * 分页查询物料图片列表
     */
    @GetMapping("/page")
    public Result<PageInfo<MaterialImage>> page(MaterialImageForm materialImageForm, PageQuery pageQuery){
        return Result.success(materialImageService.page(materialImageForm, pageQuery));
    }

}
