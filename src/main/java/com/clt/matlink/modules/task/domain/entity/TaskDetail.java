package com.clt.matlink.modules.task.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
/**
 *
 * 物料管理-盘点明细表实体类
 */
@Data
@TableName("CLT_MATERIAL_TASK_DETAIL")
@Schema(description = "物料管理-盘点明细表")
public class TaskDetail extends BaseEntity {


    @Schema(description = "id")
    private Long id;
    @Schema(description = "任务ID")
    private Long taskId;
    @Schema(description = "物料ID")
    private Long materialId;
    @Schema(description = "实际数量")
    private BigDecimal realCount;
    @Schema(description = "状态：0 正常 1盘盈 2盘亏")
    private Integer status;
}
