package com.clt.matlink.modules.base.common.domain.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class StockRecordForm {
    private Integer type;
    private Long relatedOrderId;
    private Long materialId;
    private Long stockId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}

