package com.clt.matlink.modules.task.domain.vo;

import com.clt.matlink.modules.task.domain.entity.Task;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 *
 * 物料管理-盘点任务表Vo
 */
@Data
@Schema(description = "物料管理-盘点任务表")
public class TaskVo extends Task {

    @Schema(description = "当前登陆人是否有权限审批")
    private Boolean hasAuditAuth = false;
    @Schema(description = "当前登陆人是否有权限申领出库")
    private Boolean hasApplyAuth = false;

}
