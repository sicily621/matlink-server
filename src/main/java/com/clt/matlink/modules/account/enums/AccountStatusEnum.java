package com.clt.matlink.modules.account.enums;

import com.clt.matlink.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账单状态
 *
 * @author zm
 */
@Getter
@AllArgsConstructor
public enum AccountStatusEnum implements BaseEnum {

    PENDING_APPROVAL(1, "待审批"),
    APPROVED(2, "已审批"),
    PAID_IN(3, "已收款"),
    PAID_OUT(4, "已付款");
    ;

    private final Integer value;
    private final String desc;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
