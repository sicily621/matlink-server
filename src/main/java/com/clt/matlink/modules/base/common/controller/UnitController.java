package com.clt.matlink.modules.base.common.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.base.common.domain.entity.Unit;
import com.clt.matlink.modules.base.common.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物料单位
 */
@RequestMapping("/material/unit")
@RestController
public class UnitController {
    @Autowired
    private UnitService unitService;
    /**
     * 新增物料单位
     */
    @PostMapping()
    public Result<Unit> create(@RequestBody Unit unit){
        return Result.success(unitService.save(unit));
    }
    /**
     * 修改物料单位
     * @param unit
     * @return
     */
    @PutMapping()
    public Result<Unit> update(@RequestBody Unit unit){
        return Result.success(unitService.save(unit));
    }
    /**
     * 根据物料单位Id查询物料单位
     */
    @GetMapping("{id}")
    public Result<Unit> getById(@PathVariable("id") Long id){
        return Result.success(unitService.getById(id));
    }
    /**
     * 根据物料单位Ids查询物料单位列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Unit>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(unitService.getByIds(ids));
    }

    /**
     * 删除物料单位
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        unitService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询物料单位列表
     */
    @GetMapping("/list")
    public Result<List<Unit>> list(){
        return Result.success(unitService.list());
    }
}
