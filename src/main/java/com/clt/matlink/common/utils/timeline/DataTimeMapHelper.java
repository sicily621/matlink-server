package com.clt.matlink.common.utils.timeline;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DataTimeMapHelper<T> {
    private final Integer timeType;
    private final Map<String, T> dataMap;

    public DataTimeMapHelper(Integer timeType, Date initStartDate, Date initEndDate) {
        this.timeType = timeType;
        dataMap = DataTimeUtils.ininDataMap(timeType, initStartDate, initEndDate);
    }

    public void putDataToMap(List<T> dataList, String timePropertyName) {
        DataTimeUtils.putDataToMap(timeType, dataMap, dataList, timePropertyName);
    }

    public void completionDataMap(T preValue, DataTimeUtils.DataFactory<T> handler) {
        DataTimeUtils.completionDataMap(dataMap, preValue, handler);
    }

    public Map<String, T> getDataMap() {
        return dataMap;
    }

    public static <T> DataTimeMapHelper<T> newTimeDataMap(Integer timeType, Date initStartDate, Date initEndDate) {
        DateTime date = DateUtil.date();
        if(DateUtil.compare(date, initStartDate) < 0){
            initStartDate = date;
        }
        if(DateUtil.compare(date, initEndDate) < 0){
            initEndDate = date;
        }
        return new DataTimeMapHelper<>(timeType, initStartDate, initEndDate);
    }
}
