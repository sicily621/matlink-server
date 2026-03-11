package com.clt.matlink.modules.task.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.common.excel.utils.ExcelUtil;
import com.clt.matlink.modules.base.common.domain.entity.Material;
import com.clt.matlink.modules.base.common.domain.form.MaterialForm;
import com.clt.matlink.modules.base.common.service.MaterialService;
import com.clt.matlink.modules.task.domain.entity.Task;
import com.clt.matlink.modules.task.domain.entity.TaskDetail;
import com.clt.matlink.modules.task.domain.form.TaskDetailForm;
import com.clt.matlink.modules.task.domain.vo.TaskDetailExeclVO;
import com.clt.matlink.modules.task.service.TaskDetailService;
import com.clt.matlink.modules.task.service.TaskService;
import com.google.common.collect.Lists;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.hutool.core.convert.Convert.toList;

/**
*  物料管理-盘点明细表
*/
@RequestMapping("/task/detail")
@RestController
public class TaskDetailController {

@Autowired
private TaskDetailService taskDetailService;
@Autowired
private MaterialService materialService;
@Autowired
private TaskService taskService;

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

    /**
     * 导出盘点明细表列表
     */
    @GetMapping("/export")
    public void export(TaskDetailForm taskDetailForm, HttpServletResponse response){

        List<TaskDetail> list = taskDetailService.list(taskDetailForm);
        List<Long> materialIds = Lists.newArrayList(CollStreamUtil.toSet(list, TaskDetail::getMaterialId));
        List<Material> materialList = materialService.getByIds(materialIds);
        Map<Long, Material> materialMap = CollStreamUtil.toIdentityMap(materialList, Material::getId);
        List<TaskDetailExeclVO> taskDetailExeclVOS = BeanUtil.copyToList(list, TaskDetailExeclVO.class);
        Task task = taskService.getById(taskDetailForm.getTaskId());

        for (TaskDetailExeclVO taskDetailExeclVO : taskDetailExeclVOS) {
            Long materialId = taskDetailExeclVO.getMaterialId();
            Material material = materialMap.get(materialId);
            taskDetailExeclVO.setName(task.getName());
            if(material != null){
                taskDetailExeclVO.setMaterialName(material.getName());
                taskDetailExeclVO.setBrand(material.getBrand());
                taskDetailExeclVO.setModelNo(material.getModelNo());
                taskDetailExeclVO.setSpecification(material.getSpecification());
                taskDetailExeclVO.setMinCountLimit(material.getMinCountLimit());
                taskDetailExeclVO.setMaxCountLimit(material.getMaxCountLimit());
                taskDetailExeclVO.setSafeCountLimit(material.getSafeCountLimit());
            }
        }
        ExcelUtil.exportExcel(taskDetailExeclVOS, "盘点明细表", TaskDetailExeclVO.class, response);
    }
}