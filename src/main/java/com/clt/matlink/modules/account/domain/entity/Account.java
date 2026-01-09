package com.clt.matlink.modules.account.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_account")
public class Account  extends BaseEntity {
    private Long id;
    private Long orderId;
    private String relatedCode;
    private Integer type;
    private Integer relatedEntityType;
    private Long relatedEntityId;
    private BigDecimal amount;
    private BigDecimal cost;
    private Integer paymentMethod;
    private String bankName;
    private Long employeeId;
    private Integer status;
    private String description;
}
