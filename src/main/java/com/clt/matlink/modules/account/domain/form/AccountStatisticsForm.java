package com.clt.matlink.modules.account.domain.form;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class AccountStatisticsForm {

    /** 统计类型 1日 2 月 3 年 */
    private Integer timeType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
}
