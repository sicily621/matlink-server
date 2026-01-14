package com.clt.matlink.modules.base.common.controller;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.common.domain.entity.Material;
import com.clt.matlink.modules.base.common.domain.form.MaterialForm;
import com.clt.matlink.modules.base.common.domain.vo.MaterialVO;
import com.clt.matlink.modules.base.common.service.MaterialService;
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
 * 物料
 */
@RequestMapping("/material")
@RestController
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    /**
     * 新建物料
     */
    @PostMapping()
    public Result<Material> create(@RequestBody Material material){
        return Result.success(materialService.save(material));
    }
    /**
     * 修改物料
     * @param material
     * @return
     */
    @PutMapping()
    public Result<Material> update(@RequestBody Material material){
        return Result.success(materialService.save(material));
    }
    /**
     * 批量修改物料
     * @param materials
     * @return
     */
    @PutMapping("batchUpdate")
    public Result<List<Material>> batchUpdate(@RequestBody List<Material> materials){
        return Result.success(materialService.batchSave(materials));
    }
    /**
     * 根据物料Id查询物料
     */
    @GetMapping("{id}")
    public Result<Material> getById(@PathVariable("id") Long id){
        return Result.success(materialService.getById(id));
    }
    /**
     * 根据物料Ids查询物料列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Material>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(materialService.getByIds(ids));
    }

    /**
     * 删除物料
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        materialService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询物料列表
     */
    @GetMapping("/list")
    public Result<List<Material>> list(MaterialForm materialForm){
        return Result.success(materialService.list(materialForm));
    }

    /**
     * 分页查询物料列表
     */
    @GetMapping("/page")
    public Result<PageInfo<MaterialVO>> page(MaterialForm materialForm, PageQuery pageQuery){
        return Result.success(materialService.page(materialForm, pageQuery));
    }

}
