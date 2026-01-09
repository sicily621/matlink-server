package com.clt.matlink.modules.account.service;

import com.clt.matlink.modules.account.domain.form.AccountStatisticsForm;
import com.clt.matlink.modules.account.domain.model.AccountStatisticsItem;

import java.util.List;

public interface AccountStatisticsService {

    AccountStatisticsItem getSalesStatisticsByTimeType(AccountStatisticsForm statisticsForm);

    List<AccountStatisticsItem> listSalesStatisticsByTimeType(AccountStatisticsForm statisticsForm);
}
