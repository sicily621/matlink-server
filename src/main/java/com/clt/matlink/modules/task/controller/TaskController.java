package com.clt.matlink.modules.task.controller;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.task.domain.entity.Task;
import com.clt.matlink.modules.task.domain.form.TaskForm;
import com.clt.matlink.modules.task.domain.vo.TaskVo;
import com.clt.matlink.modules.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  物料管理-盘点任务表
*/
@RequestMapping("/task")
@RestController
public class TaskController {

@Autowired
private TaskService taskService;

    /**
    * 新建物料管理-盘点任务表
    */
    @PostMapping()
    public Result<Task> create(@RequestBody Task task){
        return Result.success(taskService.save(task));
    }

    /**
    * 修改物料管理-盘点任务表
    * @param task
    * @return
    */
    @PutMapping()
    public Result<Task> update(@RequestBody Task task){
        return Result.success(taskService.save(task));
    }

    /**
    * 批量修改物料管理-盘点任务表
    * @param materials
    * @return
    */
    @PutMapping("batchUpdate")
    public Result<List<Task>> batchUpdate(@RequestBody List<Task> materials){
        return Result.success(taskService.batchSave(materials));
    }

    /**
    * 根据物料管理-盘点任务表Id查询物料管理-盘点任务表
    */
    @GetMapping("{id}")
    public Result<Task> getById(@PathVariable("id") Long id){
        return Result.success(taskService.getById(id));
    }

    /**
    * 根据物料管理-盘点任务表Ids查询物料管理-盘点任务表列表
    */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Task>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(taskService.getByIds(ids));
    }

    /**
    * 删除物料管理-盘点任务表
    */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
       taskService.deleteById(id);
        return Result.success();
    }

    /**
    * 查询物料管理-盘点任务表列表
    */
    @GetMapping("/list")
    public Result<List<TaskVo>> list(TaskForm taskForm){
        return Result.success(taskService.list(taskForm));
    }

    /**
    * 分页查询物料管理-盘点任务表列表
    */
    @GetMapping("/page")
    public Result<PageInfo<TaskVo>> page(TaskForm taskForm, PageQuery pageQuery){
        return Result.success(taskService.page(taskForm, pageQuery));
    }
    /**
     * 盘点审批
     */
    @PostMapping("/updateAuditStatus")
    public Result<Task> updateAuditStatus(
            @RequestBody
            MaterialAuditRelationParam generateParam) {
        return Result.success(taskService.updateAuditStatus(generateParam));
    }
}