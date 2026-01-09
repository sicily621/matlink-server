package com.clt.matlink.modules.account.service.Impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.common.utils.timeline.DataTimeMapHelper;
import com.clt.matlink.modules.account.domain.entity.Account;
import com.clt.matlink.modules.account.domain.form.AccountStatisticsForm;
import com.clt.matlink.modules.account.domain.model.AccountStatisticsItem;
import com.clt.matlink.modules.account.enums.AccountStatusEnum;
import com.clt.matlink.modules.account.enums.AccountTypeEnum;
import com.clt.matlink.modules.account.mapper.AccountMapper;
import com.clt.matlink.modules.account.service.AccountService;
import com.clt.matlink.modules.account.service.AccountStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AccountStatisticsServiceImpl implements AccountStatisticsService {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public AccountStatisticsItem getSalesStatisticsByTimeType(AccountStatisticsForm statisticsForm) {
        Integer timeType = statisticsForm.getTimeType();
        Date startTime = statisticsForm.getStartTime();

        DateTime startDate = null;
        DateTime endDate = null;
        if (ObjectUtil.equals(timeType,
                              1)) {
            // 日
            startDate = DateUtil.beginOfDay(startTime);
            endDate = DateUtil.endOfDay(startTime);
        } else if (ObjectUtil.equals(timeType,
                                     2)) {
            // 月
            startDate = DateUtil.beginOfMonth(startTime);
            endDate = DateUtil.endOfMonth(startTime);
        } else if (ObjectUtil.equals(timeType,
                                     3)) {
            // 年
            startDate = DateUtil.beginOfYear(startTime);
            endDate = DateUtil.endOfYear(startTime);
        }

        List<Account> accounts = getSalesAccounts(startDate,
                                                  endDate);
        AccountStatisticsItem accountStatisticsItem = new AccountStatisticsItem();
        if (CollUtil.isEmpty(accounts)) {
            accountStatisticsItem.setAmount(BigDecimal.ZERO);
            accountStatisticsItem.setCost(BigDecimal.ZERO);
            accountStatisticsItem.setProfit(BigDecimal.ZERO);
            return accountStatisticsItem;
        }

        Map<Integer, List<Account>> typeListMap = CollStreamUtil.groupByKey(accounts,
                                                                            Account::getType);
        List<Account> incomeList = typeListMap.get(AccountTypeEnum.SALES_INCOME.getValue());
        List<Account> refundList = typeListMap.get(AccountTypeEnum.SALES_REFUND.getValue());

        BigDecimal amount = getAmount(incomeList,
                                      refundList);
        BigDecimal cost = getCost(incomeList,
                                  refundList);
        BigDecimal profit = NumberUtil.sub(amount,
                                           cost);

        accountStatisticsItem.setAmount(amount);
        accountStatisticsItem.setCost(cost);
        accountStatisticsItem.setProfit(profit);
        return accountStatisticsItem;
    }

    private List<Account> getSalesAccounts(DateTime startDate, DateTime endDate) {
        LambdaQueryWrapper<Account> lqw = Wrappers.lambdaQuery();
        lqw.ge(Account::getCreateTime,
               startDate);
        lqw.le(Account::getCreateTime,
               endDate);
        lqw.in(Account::getType,
               Lists.newArrayList(AccountTypeEnum.SALES_INCOME.getValue(),
                                  AccountTypeEnum.SALES_REFUND.getValue()));
        lqw.in(Account::getStatus,
               Lists.newArrayList(AccountStatusEnum.PAID_IN.getValue(),
                                  AccountStatusEnum.PAID_OUT.getValue()));
        lqw.eq(Account::getDelFlag,
               DelFlagEnum.NORMAL.getValue());
        return accountMapper.selectList(lqw);
    }

    @Override
    public List<AccountStatisticsItem> listSalesStatisticsByTimeType(AccountStatisticsForm statisticsForm) {
        Integer timeType = statisticsForm.getTimeType();
        Date startTime = statisticsForm.getStartTime();

        DateTime startDate = null;
        DateTime endDate = null;
        if (ObjectUtil.equals(timeType,
                              1)) {
            // 月的日统计数据
            startDate = DateUtil.beginOfMonth(startTime);
            endDate = DateUtil.endOfMonth(startTime);
        } else if (ObjectUtil.equals(timeType,
                                     2)) {
            // 年的月统计数据
            startDate = DateUtil.beginOfYear(startTime);
            endDate = DateUtil.endOfYear(startTime);
        }
        List<Account> accounts = getSalesAccounts(startDate,
                                                  endDate);
        List<Account> newAccounts = Lists.newArrayList(accounts);


        DataTimeMapHelper<AccountStatisticsItem> timeMapHelper = DataTimeMapHelper.newTimeDataMap(timeType,
                                                                                                  startDate,
                                                                                                  endDate);
        Map<String, AccountStatisticsItem> dataMap = timeMapHelper.getDataMap();
        for (Account newAccount : newAccounts) {
            Date createTime = newAccount.getCreateTime();
            String formatKey = null;
            if (ObjectUtil.equals(timeType,
                                  1)) {
                // 月
                formatKey = DateUtil.format(createTime,
                                            "yyyy-MM-dd");
            } else if (ObjectUtil.equals(timeType,
                                         2)) {
                // 年
                formatKey = DateUtil.format(createTime,
                                            "yyyy-MM-01");
            }
            AccountStatisticsItem old = dataMap.get(formatKey);
            if(old == null){
                old = AccountStatisticsItem.empty();
            }

            BigDecimal newAmount = newAccount.getAmount();
            // 退款取相反数
            if(newAccount.getType().equals(AccountTypeEnum.SALES_REFUND.getValue())){
                newAmount = newAmount.negate();
            }
            old.setAmount(NumberUtil.add(old.getAmount(),
                                         newAmount));

            BigDecimal newCost = newAccount.getCost();
            // 退款取相反数
            if(newAccount.getType().equals(AccountTypeEnum.SALES_REFUND.getValue())){
                newCost = newCost.negate();
            }
            old.setCost(NumberUtil.add(old.getCost(),
                                       newCost));
            dataMap.put(formatKey, old);
        }
        // 补全空数据
        Set<Map.Entry<String, AccountStatisticsItem>> entries = dataMap.entrySet();
        for (Map.Entry<String, AccountStatisticsItem> entry : entries) {
            String key = entry.getKey();
            AccountStatisticsItem value = entry.getValue();
            if(value == null){
                dataMap.put(key, AccountStatisticsItem.empty());
            }else{
                value.setProfit(NumberUtil.sub(value.getAmount(), value.getCost()));
            }
        }

        List<AccountStatisticsItem> fillList = Lists.newArrayList(dataMap.values());
        return fillList;
    }

    private BigDecimal getAmount(List<Account> incomeList, List<Account> refundList) {
        List<BigDecimal> inAmount = CollStreamUtil.toList(incomeList,
                                                          Account::getAmount);
        List<BigDecimal> outAmount = CollStreamUtil.toList(refundList,
                                                           Account::getAmount);
        // 收入相加
        BigDecimal inAmountTotal = inAmount.stream()
                                           .reduce((tagValue1, tagValue2) -> NumberUtil.add(tagValue1,
                                                                                            tagValue2))
                                           .get();
        // 退款相加
        BigDecimal outAmountTotal = outAmount.stream()
                                             .reduce((tagValue1, tagValue2) -> NumberUtil.add(tagValue1,
                                                                                              tagValue2))
                                             .get();
        // 最终收入，收入减去退款
        BigDecimal finalAmount = NumberUtil.sub(inAmountTotal,
                                                outAmountTotal);

        return finalAmount;
    }

    private BigDecimal getCost(List<Account> incomeList, List<Account> refundList) {
        List<BigDecimal> inAmount = CollStreamUtil.toList(incomeList,
                                                          Account::getCost);
        List<BigDecimal> outAmount = CollStreamUtil.toList(refundList,
                                                           Account::getCost);
        // 成本相加
        BigDecimal inAmountTotal = inAmount.stream()
                                           .reduce((tagValue1, tagValue2) -> NumberUtil.add(tagValue1,
                                                                                            tagValue2))
                                           .get();
        // 退款成本相加
        BigDecimal outAmountTotal = outAmount.stream()
                                             .reduce((tagValue1, tagValue2) -> NumberUtil.add(tagValue1,
                                                                                              tagValue2))
                                             .get();
        // 最终收入，收入减去退款
        BigDecimal finalAmount = NumberUtil.sub(inAmountTotal,
                                                outAmountTotal);

        return finalAmount;
    }
}
