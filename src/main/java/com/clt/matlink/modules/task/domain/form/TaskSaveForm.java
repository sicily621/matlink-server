package com.clt.matlink.modules.task.domain.form;

import com.clt.matlink.modules.task.domain.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
* 物料管理-盘点任务表查询参数
* @author min
* @date 2026-02-26 16:32:16
*/
@Data
public class TaskSaveForm extends Task {
    private Long deptId;
}