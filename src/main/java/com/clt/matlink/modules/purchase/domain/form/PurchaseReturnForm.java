package com.clt.matlink.modules.purchase.domain.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PurchaseReturnForm {
    private String code;
    private Long orderId;
    private Long employeeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer status;
    private Integer startStatus;
    private Integer endStatus;
    private Integer receipt;
}
