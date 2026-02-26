package com.clt.matlink.modules.task.domain.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
* 物料管理-盘点明细表查询参数
* @author min
* @date 2026-02-26 16:32:16
*/
@Data
@Schema(description = "物料管理-盘点明细表查询参数")
public class TaskDetailForm {

    private Long taskId;

}