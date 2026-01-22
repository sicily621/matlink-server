package com.clt.matlink.modules.outBoundApply.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OutBoundApplyForm  extends PageQuery{
    private Long stockId;
    private String applyNo;
    private Integer auditStatus;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private Long applyUserId;
    private Long createUserId;
}
