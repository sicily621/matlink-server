package com.clt.matlink.modules.task.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollStreamUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.task.domain.entity.TaskDetail;
import com.clt.matlink.modules.task.domain.form.TaskDetailForm;
import com.clt.matlink.modules.task.mapper.TaskDetailMapper;
import com.clt.matlink.modules.task.service.TaskDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskDetailServiceImpl implements TaskDetailService {

    @Autowired
    private TaskDetailMapper taskDetailMapper;

    @Override
    public TaskDetail save(TaskDetail taskDetail) {
        int flag = 0;
        if(taskDetail.getId()==null){
            flag= taskDetailMapper.insert(taskDetail);
        }else{
            flag = taskDetailMapper.updateById(taskDetail);
        }
        if(flag>0){
            return taskDetailMapper.selectById(taskDetail.getId());
        }else{
            return null;
        }
    }

    @Override
    public TaskDetail getById(Long id) {
        return taskDetailMapper.selectById(id);
    }

    @Override
    public List<TaskDetail> getByIds(List<Long> ids) {
        LambdaQueryWrapper<TaskDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(TaskDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( TaskDetail::getId, ids);
        return taskDetailMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        taskDetailMapper.deleteById(id);
        return true;
    }

    @Override
    public List<TaskDetail> list(TaskDetailForm form) {
        LambdaQueryWrapper<TaskDetail> lqw = getQueryWrapper(form);
        List<TaskDetail> list = taskDetailMapper.selectList(lqw);
        List<TaskDetail> voList = BeanUtil.copyToList(list, TaskDetail.class);
        return voList;
    }

    @Override
    public PageInfo<TaskDetail> page(TaskDetailForm form, PageQuery pageQuery) {

        LambdaQueryWrapper<TaskDetail> lqw = getQueryWrapper(form);
        Page<TaskDetail> page = pageQuery.build();
        Page<TaskDetail> result = taskDetailMapper.selectPage(page, lqw);
        PageInfo<TaskDetail> tableDataInfo = PageInfo.build(result,TaskDetail.class );

        return tableDataInfo;
    }

    @Override
    public List<TaskDetail> batchSave(List<TaskDetail> list) {
        taskDetailMapper.insertOrUpdateBatch(list);
        List<Long> ids = CollStreamUtil.toList(list, TaskDetail::getId);
        List<TaskDetail> result = getByIds(ids);
        return result;
    }

    private LambdaQueryWrapper<TaskDetail> getQueryWrapper(TaskDetailForm form) {
        LambdaQueryWrapper<TaskDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(form.getTaskId()!=null, TaskDetail::getTaskId, form.getTaskId());
        lqw.eq(TaskDetail::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}