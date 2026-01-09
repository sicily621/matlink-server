package com.clt.matlink.modules.account.domain.model;


import lombok.Data;

import java.math.BigDecimal;


@Data
public class AccountStatisticsItem {

    /** 收款 */
    private BigDecimal amount;
    /** 成本 */
    private BigDecimal cost;
    /** 利润 */
    private BigDecimal profit;

    public static AccountStatisticsItem empty() {
        AccountStatisticsItem item = new AccountStatisticsItem();
        item.setAmount(BigDecimal.ZERO);
        item.setCost(BigDecimal.ZERO);
        item.setProfit(BigDecimal.ZERO);
        return item;
    }
}
