package com.clt.matlink.modules.sales.domain.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SalesOrderForm {
    private String code;
    private Long customerId;
    private Long employeeId;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Integer startStatus;
    private Integer endStatus;
}
