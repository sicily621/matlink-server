package com.clt.matlink.modules.sales.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("hg_sales_order")
public class SalesOrder extends BaseEntity {
    private Long id;
    private String code;
    private Long customerId;
    private Long employeeId;
    private Date expectedDate;
    private Date actualDate;
    private Integer status;
    private BigDecimal totalAmount;
    private BigDecimal discount;
    private BigDecimal tax;
    private BigDecimal finalAmount;
    private String description;
    private Long approverId;
    private Date approvalTime;
}
