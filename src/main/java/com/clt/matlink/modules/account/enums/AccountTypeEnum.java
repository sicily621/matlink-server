package com.clt.matlink.modules.account.enums;

import com.clt.matlink.common.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账单类型
 *
 * @author zm
 */
@Getter
@AllArgsConstructor
public enum AccountTypeEnum implements BaseEnum {

    PURCHASE_PAYMENT(1, "采购支付"),
    PURCHASE_REFUND(2, "采购退款"),
    SALES_INCOME(3, "销售收入"),
    SALES_REFUND(4, "销售退款"),
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
