package com.clt.matlink.modules.account.controller;

import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.account.domain.form.AccountStatisticsForm;
import com.clt.matlink.modules.account.domain.model.AccountStatisticsItem;
import com.clt.matlink.modules.account.service.AccountStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 账单统计
 */
@RequestMapping("/account/statistics")
@RestController
public class AccountStatisticsController {
    @Autowired
    private AccountStatisticsService accountStatisticsService;

    /**
     * 查询单个时间类型统计
     */
    @GetMapping("/getSalesStatisticsByTimeType")
    public Result<AccountStatisticsItem> getSalesStatisticsByTimeType(AccountStatisticsForm statisticsForm){
        return Result.success(accountStatisticsService.getSalesStatisticsByTimeType(statisticsForm));
    }

    /**
     * 查询多个时间类型统计
     */
    @GetMapping("/listSalesStatisticsByTimeType")
    public Result<List<AccountStatisticsItem>> listSalesStatisticsByTimeType(AccountStatisticsForm statisticsForm){
        return Result.success(accountStatisticsService.listSalesStatisticsByTimeType(statisticsForm));
    }
}
