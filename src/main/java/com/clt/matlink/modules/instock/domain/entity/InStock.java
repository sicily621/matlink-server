package com.clt.matlink.modules.instock.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clt_material_in_stock")
public class InStock extends BaseEntity {
    private Long id;
    private Long originOrderId;
    private Long stockId;
    private String inStockNo;
    private Integer auditStatus;
    private Integer status;
    private Long supplyId;
    private Integer type;
    private String description;
    private Integer isDirect;
    private Long inStockUserId;
    private Date inStockTime;
    private Long auditUserId;
    private Date auditTime;
    private Long createUserId;
    private Long source;
}
