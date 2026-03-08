package com.clt.matlink.modules.base.common.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.base.common.domain.entity.StockRecord;
import com.clt.matlink.modules.base.common.domain.form.StockRecordForm;
import com.clt.matlink.modules.base.common.domain.form.StockTrendForm;
import com.clt.matlink.modules.base.common.domain.vo.MaterialCostPriceVO;
import com.clt.matlink.modules.base.common.domain.vo.StockFlowVO;
import com.clt.matlink.modules.base.common.domain.vo.StockTrendVO;
import com.clt.matlink.modules.base.common.mapper.StockRecordMapper;
import com.clt.matlink.modules.base.common.service.StockRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockRecordServiceImpl implements StockRecordService {
    @Autowired
    private StockRecordMapper stockRecordMapper;
    @Override
    public StockRecord save(StockRecord stockRecord) {
        int flag = 0;
        if(stockRecord.getId()==null){
            flag= stockRecordMapper.insert(stockRecord);
        }else{
            flag = stockRecordMapper.updateById(stockRecord);
        }
        if(flag>0){
            return stockRecordMapper.selectById(stockRecord.getId());
        }else{
            return null;
        }

    }

    @Override
    public StockRecord getById(Long id) {
        return stockRecordMapper.selectById(id);
    }

    @Override
    public List<StockRecord> getByIds(List<Long> ids) {
        LambdaQueryWrapper<StockRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(StockRecord::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( StockRecord::getId, ids);
        return stockRecordMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        stockRecordMapper.deleteById(id);
        return true;
    }

    @Override
    public List<StockRecord> list(StockRecordForm stockRecordForm) {
        LambdaQueryWrapper<StockRecord> lqw = getQueryWrapper(stockRecordForm);
        return stockRecordMapper.selectList(lqw);
    }
    @Override
    public PageInfo<StockRecord> page(StockRecordForm stockRecordForm, PageQuery pageQuery) {
        LambdaQueryWrapper<StockRecord> lqw = getQueryWrapper(stockRecordForm);
        Page<StockRecord> page = pageQuery.build();
        Page<StockRecord> result = stockRecordMapper.selectPage(page, lqw);
        PageInfo<StockRecord> tableDataInfo = PageInfo.build(result, StockRecord.class);
        return tableDataInfo;
    }
    private LambdaQueryWrapper<StockRecord> getQueryWrapper(StockRecordForm stockRecordForm) {
        LambdaQueryWrapper<StockRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(stockRecordForm.getStockId()!=null, StockRecord::getStockId, stockRecordForm.getStockId());
        lqw.eq(stockRecordForm.getMaterialId()!=null, StockRecord::getMaterialId, stockRecordForm.getMaterialId());
        lqw.eq(stockRecordForm.getType()!=null, StockRecord::getType, stockRecordForm.getType());
        lqw.eq(stockRecordForm.getRelatedOrderId()!=null, StockRecord::getRelatedOrderId, stockRecordForm.getRelatedOrderId());
        lqw.ge(stockRecordForm.getStartTime()!=null, StockRecord::getHandleTime, stockRecordForm.getStartTime());
        lqw.le(stockRecordForm.getEndTime()!=null, StockRecord::getHandleTime, stockRecordForm.getEndTime());
        lqw.eq( StockRecord::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }

    @Override
    public List<MaterialCostPriceVO>getMaterialCostPriceTrend(StockTrendForm form){
        // 1. 时间转换
        LocalDate start = toLocal(form.getStartTime());
        LocalDate end = toLocal(form.getEndTime());

        // 【新增】获取“今天”的日期，用于截断
        LocalDate today = LocalDate.now(ZONE);

        // 2. 查库 (假设 Mapper 已按时间升序排列)
        List<MaterialCostPriceVO> dbList = stockRecordMapper.getMaterialCostPriceTrend(form);;

        // 3. 算期初成本
        BigDecimal costPrice = calcInitialCostPrice(dbList);

        // 4. 建 Map 索引
        Map<LocalDate, MaterialCostPriceVO> map = dbList.stream()
                .filter(v -> v.getHandleTime() != null)
                .collect(Collectors.toMap(
                        v -> toLocal(v.getHandleTime()),
                        v -> v,
                        (a, b) -> a
                ));

        // 5. 核心循环
        List<MaterialCostPriceVO> result = new ArrayList<>();
        long totalDays = ChronoUnit.DAYS.between(start, end);

        for (int i = 0; i <= totalDays; i++) {
            LocalDate date = start.plusDays(i);

            // 🔥【核心截断逻辑】：如果日期超过今天，直接停止，不生成未来数据
            if (date.isAfter(today)) {
                break;
            }

            MaterialCostPriceVO real = map.get(date);

            if (real != null) {
                // --- 有真实数据 ---
                result.add(real);
                if (real.getCostPrice() != null) {
                    costPrice = real.getCostPrice();
                }
            } else {
                // --- 无数据：补全 (只补全到今天) ---
                MaterialCostPriceVO fake = new MaterialCostPriceVO();
                fake.setHandleTime(toDate(date));
                fake.setCostPrice(costPrice);
                result.add(fake);
            }
        }
        return result;
    }

    // 【关键】固定时区
    private static final ZoneId ZONE = ZoneId.of("Asia/Shanghai");


    @Override
    public List<StockTrendVO> getStockTrend(StockTrendForm form){
        // 1. 时间转换
        LocalDate start = toLocal(form.getStartTime());
        LocalDate end = toLocal(form.getEndTime());

        // 【新增】获取“今天”的日期，用于截断
        LocalDate today = LocalDate.now(ZONE);

        // 2. 查库 (假设 Mapper 已按时间升序排列)
        List<StockTrendVO> dbList = stockRecordMapper.getStockTrend(form);;

        // 3. 算期初余额
        BigDecimal balance = calcInitialBalance(dbList);

        // 4. 建 Map 索引
        Map<LocalDate, StockTrendVO> map = dbList.stream()
                .filter(v -> v.getHandleTime() != null)
                .collect(Collectors.toMap(
                        v -> toLocal(v.getHandleTime()),
                        v -> v,
                        (a, b) -> a
                ));

        // 5. 核心循环
        List<StockTrendVO> result = new ArrayList<>();
        long totalDays = ChronoUnit.DAYS.between(start, end);

        for (int i = 0; i <= totalDays; i++) {
            LocalDate date = start.plusDays(i);

            // 🔥【核心截断逻辑】：如果日期超过今天，直接停止，不生成未来数据
            if (date.isAfter(today)) {
                break;
            }

            StockTrendVO real = map.get(date);

            if (real != null) {
                // --- 有真实数据 ---
                result.add(real);
                if (real.getBalanceAfter() != null) {
                    balance = real.getBalanceAfter();
                }
            } else {
                // --- 无数据：补全 (只补全到今天) ---
                StockTrendVO fake = new StockTrendVO();
                fake.setHandleTime(toDate(date));
                fake.setQuantityChange(BigDecimal.ZERO);
                fake.setBalanceAfter(balance);
                result.add(fake);
            }
        }
        return result;
    }
    @Override
    public List<StockFlowVO> getStockFlowStatistics(StockTrendForm form){
        // 1. 时间转换
        LocalDate start = toLocal(form.getStartTime());
        LocalDate end = toLocal(form.getEndTime());

        // 【新增】获取“今天”的日期，用于截断
        LocalDate today = LocalDate.now(ZONE);

        // 2. 查库 (假设 Mapper 已按时间升序排列)
        List<StockFlowVO> dbList = stockRecordMapper.getStockFlowStatistics(form);;

        // 3. 建 Map 索引
        Map<LocalDate, StockFlowVO> map = dbList.stream()
                .filter(v -> v.getHandleTime() != null)
                .collect(Collectors.toMap(
                        v -> toLocal(v.getHandleTime()),
                        v -> v,
                        (a, b) -> a
                ));

        // 4. 核心循环
        List<StockFlowVO> result = new ArrayList<>();
        long totalDays = ChronoUnit.DAYS.between(start, end);

        for (int i = 0; i <= totalDays; i++) {
            LocalDate date = start.plusDays(i);

            // 🔥【核心截断逻辑】：如果日期超过今天，直接停止，不生成未来数据
            if (date.isAfter(today)) {
                break;
            }

            StockFlowVO real = map.get(date);

            if (real != null) {
                // --- 有真实数据 ---
                result.add(real);

            }else{
                // --- 无数据：补全 (只补全到今天) ---
                StockFlowVO fake = new StockFlowVO();
                fake.setHandleTime(toDate(date));
                fake.setOutStockCount(BigDecimal.ZERO);
                fake.setInStockCount(BigDecimal.ZERO);
                result.add(fake);
            }
        }
        return result;
    }

    // ================= 辅助方法 =================

    private BigDecimal calcInitialBalance(List<StockTrendVO> list) {
        if (list.isEmpty()) return BigDecimal.ZERO;
        StockTrendVO first = list.get(0);
        BigDecimal bAfter = first.getBalanceAfter() != null ? first.getBalanceAfter() : BigDecimal.ZERO;
        BigDecimal bChange = first.getQuantityChange() != null ? first.getQuantityChange() : BigDecimal.ZERO;
        BigDecimal initial = bAfter.subtract(bChange);
        return initial.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : initial;
    }
    private BigDecimal calcInitialCostPrice(List<MaterialCostPriceVO> list) {
        if (list.isEmpty()) return BigDecimal.ZERO;
        MaterialCostPriceVO first = list.get(0);
        BigDecimal initial = first.getCostPrice() != null ? first.getCostPrice() : BigDecimal.ZERO;
        return initial.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : initial;
    }

    private LocalDate toLocal(Date date) {
        if (date == null) return null;
        return Instant.ofEpochMilli(date.getTime()).atZone(ZONE).toLocalDate();
    }

    private Date toDate(LocalDate localDate) {
        if (localDate == null) return null;
        return Date.from(localDate.atStartOfDay(ZONE).toInstant());
    }


}
