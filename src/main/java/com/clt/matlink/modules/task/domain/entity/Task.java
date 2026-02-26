package com.clt.matlink.modules.task.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
/**
 *
 * 物料管理-盘点任务表实体类
 */
@Data
@TableName("CLT_MATERIAL_TASK")
@Schema(description = "物料管理-盘点任务表")
public class Task extends BaseEntity {


    @Schema(description = "id")
    private Long id;
    @Schema(description = "名称")
    private String name;
    @Schema(description = "盘点类型：1-全库盘点，2-区域盘点")
    private Integer type;
    @Schema(description = "库ID")
    private Long stockId;
    @Schema(description = "处理人ID")
    private Long handleUserId;
    @Schema(description = "开始时间")
    private Long startTime;
    @Schema(description = "结束时间")
    private Long endTime;
    @Schema(description = "是否盘点0库存：0-否，1-是")
    private Integer stocktakingNone;
    @Schema(description = "审批时间")
    private Date auditTime;
    @Schema(description = "描述")
    private String description;
    @Schema(description = "审批状态：0-未审核，1-审核中，2-已审核")
    private Integer auditStatus;
    @Schema(description = "审批人id")
    private Long auditUserId;
    @Schema(description = "盘点状态：0-未盘点，1-已盘点结束，2-已入库，3-已废弃，4-盘盈或盘亏")
    private Integer status;
    @Schema(description = "创建用户id")
    private Long createUserId;
}
