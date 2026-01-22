package com.clt.matlink.modules.purchase.domain.form;

import com.clt.matlink.common.domain.form.PageQuery;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class PurchaseDetailForm  {
    private Long billId;
}
