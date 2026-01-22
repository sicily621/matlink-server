package com.clt.matlink.modules.purchase.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clt_purchasing_requisition")
public class Purchase extends BaseEntity {
    private Long id;
    private String billNo;
    private Long stockId;
    private Long applyUserId;
    private Long deptId;
    private Date applyDate;
    private Integer auditStatus;
    private Integer status;
    private Long createUserId;
    private String description;
    private Long auditUserId;
    private Date auditTime;
}
