package com.clt.matlink.modules.purchase.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("clt_purchasing_requisition_details")
public class PurchaseDetail extends BaseEntity {
    private Long id;
    private Long billId;
    private Long materialTypeId;
    private Long materialId;
    private BigDecimal count;
    private String brand;
    private String modelNo;
    private BigDecimal perPrice;
    private BigDecimal totalPrice;
    private Long supplierId;
    private Date deliveryDate;
    private String description;
    private String filePath;
}
