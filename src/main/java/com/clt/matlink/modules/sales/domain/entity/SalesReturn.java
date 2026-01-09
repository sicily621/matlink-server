package com.clt.matlink.modules.sales.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("hg_sales_return")
public class SalesReturn  extends BaseEntity {
    private Long id;
    private String code;
    private Long orderId;
    private Long employeeId;
    private Integer status;
    private Long customerId;
    private String description;
    private BigDecimal totalAmount;
    private Long approverId;
    private Date approvalTime;
}
