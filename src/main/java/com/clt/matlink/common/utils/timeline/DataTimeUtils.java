package com.clt.matlink.common.utils.timeline;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import com.google.common.collect.Maps;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataTimeUtils {

    private static final DateTimeFormatter DATETIME_FORMATTER_HOUR = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00");
    private static final DateTimeFormatter DATETIME_FORMATTER_DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter DATETIME_FORMATTER_MONTH = DateTimeFormatter.ofPattern("yyyy-MM-01");
    private static final DateTimeFormatter DATETIME_FORMATTER_YEAR = DateTimeFormatter.ofPattern("yyyy-01-01");

    private static Date getStartOfCurrentHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private static Date getEndOfCurrentHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取查询的开始时间
     *
     * @param timeType
     * @param startTime
     * @return
     */
    public static Date getQueryStartDate(Integer timeType, Date startTime) {
        Date startDate = null;
        // 时表
        if (timeType == 0) {
            startDate = DateUtil.beginOfHour(startTime);
            // 日表
        } else if (timeType == 1) {
            startDate = DateUtil.beginOfDay(startTime);
            // 月表
        } else if (timeType == 2) {
            startDate = DateUtil.beginOfMonth(startTime);
            // 年表
        } else if (timeType == 3) {
            startDate = DateUtil.beginOfYear(startTime);
        } else {
            throw new RuntimeException("时间类型不支持");
        }
        return startDate;
    }

    /**
     * 获取查询的结束时间
     *
     * @param timeType
     * @param endTime
     * @return
     */
    public static Date getQueryEndDate(Integer timeType, Date endTime) {
        Date endDate = null;

        // 时表
        if (timeType == 0) {
            endDate = DateUtil.endOfHour(endTime);
            // 日表
        } else if (timeType == 1) {
            // 本日最后一秒
            endDate = DateUtil.endOfDay(endTime);
            // 月表
        } else if (timeType == 2) {
            // 本月最后一秒
            endDate = DateUtil.endOfMonth(endTime);
        } else if (timeType == 3) {
            // 本年最后一秒
            endDate = DateUtil.endOfYear(endTime);
        } else {
            throw new RuntimeException("时间类型不支持");
        }
        return endDate;
    }

    /**
     * 根据开始时间和结束时间初始化数据map，日期为key，EnergyData为值
     *
     * @param timeType
     * @param startDate
     * @param endDate
     * @return
     */
    public static <T> Map<String, T> ininDataMap(Integer timeType, Date startDate, Date endDate) {

        // key: dateStr, value:T
        Map<String, T> dataMap = Maps.newTreeMap();
        // 时表
        if (timeType == 0) {
            // 时表每小时计算一次，当前小时还没计算，不加1
            int hours = LocalDateTimeUtil.getHoursBetween(startDate, endDate) + 1;
            LocalDateTime tempDateTime = LocalDateTimeUtil.date2LocalDateTime(startDate);
            String tempDateKey = tempDateTime.format(DATETIME_FORMATTER_HOUR);
            for (int i = 0; i < hours; i++) {
                dataMap.put(tempDateKey, null);
                tempDateTime = tempDateTime.plus(1, ChronoUnit.HOURS);
                tempDateKey = tempDateTime.format(DATETIME_FORMATTER_HOUR);
            }
            // 日表
        } else if (timeType == 1) {
            int days = LocalDateTimeUtil.getDaysBetween(startDate, endDate) + 1;
            LocalDateTime tempDateTime = LocalDateTimeUtil.date2LocalDateTime(startDate);
            String tempDateKey = tempDateTime.format(DATETIME_FORMATTER_DAY);
            for (int i = 0; i < days; i++) {
                dataMap.put(tempDateKey, null);
                tempDateTime = tempDateTime.plus(1, ChronoUnit.DAYS);
                tempDateKey = tempDateTime.format(DATETIME_FORMATTER_DAY);
            }
            // 月表
        } else if (timeType == 2) {
            int months = LocalDateTimeUtil.getMonthsBetween(startDate, endDate) + 1;
            LocalDateTime tempDateTime = LocalDateTimeUtil.date2LocalDateTime(startDate);
            String tempDateKey = tempDateTime.format(DATETIME_FORMATTER_MONTH);
            for (int i = 0; i < months; i++) {
                dataMap.put(tempDateKey, null);
                tempDateTime = tempDateTime.plus(1, ChronoUnit.MONTHS);
                tempDateKey = tempDateTime.format(DATETIME_FORMATTER_MONTH);
            }
            // 年表
        } else if (timeType == 3) {
            int years = LocalDateTimeUtil.getYearsBetween(startDate, endDate) + 1;
            LocalDateTime tempDateTime = LocalDateTimeUtil.date2LocalDateTime(startDate);
            String tempDateKey = tempDateTime.format(DATETIME_FORMATTER_YEAR);
            for (int i = 0; i < years; i++) {
                dataMap.put(tempDateKey, null);
                tempDateTime = tempDateTime.plus(1, ChronoUnit.YEARS);
                tempDateKey = tempDateTime.format(DATETIME_FORMATTER_YEAR);
            }
        }
        return dataMap;
    }

    /**
     * 将数据填入map
     *
     * @param timeType
     * @param dataMap
     * @param dataList
     */
    public static <T> void putDataToMap(Integer timeType, Map<String, T> dataMap, List<T> dataList,
                                        String timePropertyName) {
        if (dataList == null) {
            return;
        }
        for (T energyData : dataList) {
            Date curTime = null;
            try {
                curTime = (Date) ReflectUtil.getFieldValue(energyData, timePropertyName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // 时表
            if (timeType == 0) {
                LocalDateTime tempDateTime = LocalDateTimeUtil.date2LocalDateTime(curTime);
                String tempDateKey = tempDateTime.format(DATETIME_FORMATTER_HOUR);
                dataMap.put(tempDateKey, energyData);
                // 日表
            } else if (timeType == 1) {
                LocalDateTime tempDateTime = LocalDateTimeUtil.date2LocalDateTime(curTime);
                String tempDateKey = tempDateTime.format(DATETIME_FORMATTER_DAY);
                dataMap.put(tempDateKey, energyData);
                // 月表
            } else if (timeType == 2) {
                LocalDateTime tempDateTime = LocalDateTimeUtil.date2LocalDateTime(curTime);
                String tempDateKey = tempDateTime.format(DATETIME_FORMATTER_MONTH);
                dataMap.put(tempDateKey, energyData);
                // 年表
            } else if (timeType == 3) {
                LocalDateTime tempDateTime = LocalDateTimeUtil.date2LocalDateTime(curTime);
                String tempDateKey = tempDateTime.format(DATETIME_FORMATTER_YEAR);
                dataMap.put(tempDateKey, energyData);
            }
        }
    }

    /**
     * 如果没有值则用前一个有值的补全
     *
     * @param dataMap
     * @param preValue
     */
    public static <T> void completionDataMap(Map<String, T> dataMap, T preValue, DataFactory<T> handler) {
        Set<Map.Entry<String, T>> entries = dataMap.entrySet();
        T tempPreValue = preValue;
        for (Map.Entry<String, T> entry : entries) {
            String dateStr = entry.getKey();
            T data = entry.getValue();
            if (data == null) {
                T newInstance = handler.getNewData(tempPreValue, dateStr);
                dataMap.put(dateStr, newInstance);
            } else {
                tempPreValue = data;
            }
        }

    }

    public static abstract class DataFactory<T> {
        /**
         * 获取新设备对象
         *
         * @param preValue
         * @param dateStr
         * @return
         */
        public abstract T getNewData(T tempPreValue, String dateStr);
    }

}
