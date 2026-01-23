package com.clt.matlink.modules.outBoundApply.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clt_material_outbound_apply")
public class OutBoundApply extends BaseEntity {
    private Long id;
    private Long stockId;
    private Long workorderId;
    private String applyNo;
    private Date applyTime;
    private Long deptId;
    private Long applyUserId;
    private Integer auditStatus;
    private Integer status;
    private Long auditUserId;
    private Date auditTime;
    private String purpose;
    private String description;
    private Long createUserId;
}
