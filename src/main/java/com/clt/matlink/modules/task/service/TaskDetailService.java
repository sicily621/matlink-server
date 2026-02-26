package com.clt.matlink.modules.task.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.task.domain.entity.TaskDetail;
import com.clt.matlink.modules.task.domain.form.TaskDetailForm;

import java.util.List;

public interface TaskDetailService {

    TaskDetail save(TaskDetail taskDetail);

    TaskDetail getById(Long id);

    List<TaskDetail> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<TaskDetail> list(TaskDetailForm form);

    PageInfo<TaskDetail> page(TaskDetailForm form, PageQuery pageQuery);

    List<TaskDetail> batchSave(List<TaskDetail> list);
}