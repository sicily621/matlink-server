package com.clt.matlink.modules.task.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.task.domain.entity.TaskDetail;
import com.clt.matlink.modules.task.domain.form.TaskDetailForm;
import com.clt.matlink.modules.task.service.TaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  物料管理-盘点明细表
*/
@RequestMapping("/task/detail")
@RestController
public class TaskDetailController {

@Autowired
private TaskDetailService taskDetailService;

    /**
    * 新建物料管理-盘点明细表
    */
    @PostMapping()
    public Result<TaskDetail> create(@RequestBody TaskDetail taskDetail){
        return Result.success(taskDetailService.save(taskDetail));
    }

    /**
    * 修改物料管理-盘点明细表
    * @param taskDetail
    * @return
    */
    @PutMapping()
    public Result<TaskDetail> update(@RequestBody TaskDetail taskDetail){
        return Result.success(taskDetailService.save(taskDetail));
    }

    /**
    * 批量修改物料管理-盘点明细表
    * @param materials
    * @return
    */
    @PutMapping("batchUpdate")
    public Result<List<TaskDetail>> batchUpdate(@RequestBody List<TaskDetail> materials){
        return Result.success(taskDetailService.batchSave(materials));
    }

    /**
    * 根据物料管理-盘点明细表Id查询物料管理-盘点明细表
    */
    @GetMapping("{id}")
    public Result<TaskDetail> getById(@PathVariable("id") Long id){
        return Result.success(taskDetailService.getById(id));
    }

    /**
    * 根据物料管理-盘点明细表Ids查询物料管理-盘点明细表列表
    */
    @GetMapping("/getByIds/{ids}")
    public Result<List<TaskDetail>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(taskDetailService.getByIds(ids));
    }

    /**
    * 删除物料管理-盘点明细表
    */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
       taskDetailService.deleteById(id);
        return Result.success();
    }

    /**
    * 查询物料管理-盘点明细表列表
    */
    @GetMapping("/list")
    public Result<List<TaskDetail>> list(TaskDetailForm taskDetailForm){
        return Result.success(taskDetailService.list(taskDetailForm));
    }

    /**
    * 分页查询物料管理-盘点明细表列表
    */
    @GetMapping("/page")
    public Result<PageInfo<TaskDetail>> page(TaskDetailForm taskDetailForm, PageQuery pageQuery){
        return Result.success(taskDetailService.page(taskDetailForm, pageQuery));
    }
}