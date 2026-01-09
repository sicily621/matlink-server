package com.clt.matlink.modules.purchase.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("hg_purchase_order")
public class PurchaseOrder extends BaseEntity {
    private Long id;
    private Long demandId;
    private String code;
    private Long employeeId;
    private Date expectedDate;
    private Date actualDate;
    private Integer status;
    private BigDecimal totalAmount;
    private String description;
    private Long approverId;
    private Date approvalTime;
}
