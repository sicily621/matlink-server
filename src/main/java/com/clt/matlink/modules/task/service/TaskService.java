package com.clt.matlink.modules.task.service;

import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.modules.flow.domain.form.MaterialAuditRelationParam;
import com.clt.matlink.modules.outBoundApply.domain.entity.OutBoundApply;
import com.clt.matlink.modules.task.domain.entity.Task;
import com.clt.matlink.modules.task.domain.form.TaskForm;
import com.clt.matlink.modules.task.domain.vo.TaskVo;

import java.util.List;

public interface TaskService {

    Task save(Task task);

    Task getById(Long id);

    List<Task> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<TaskVo> list(TaskForm form);

    PageInfo<TaskVo> page(TaskForm form, PageQuery pageQuery);

    List<Task> batchSave(List<Task> list);
    Task updateAuditStatus(MaterialAuditRelationParam generateParam);
}