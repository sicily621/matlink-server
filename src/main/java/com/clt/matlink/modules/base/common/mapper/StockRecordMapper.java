package com.clt.matlink.modules.base.common.mapper;

import com.clt.matlink.common.mybatis.mapper.BaseMapperPlus;
import com.clt.matlink.modules.base.common.domain.entity.StockRecord;
import com.clt.matlink.modules.base.common.domain.form.StockTrendForm;
import com.clt.matlink.modules.base.common.domain.vo.MaterialCostPriceVO;
import com.clt.matlink.modules.base.common.domain.vo.StockFlowVO;
import com.clt.matlink.modules.base.common.domain.vo.StockTrendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StockRecordMapper extends BaseMapperPlus<StockRecord, StockRecord> {

    List<StockTrendVO> getStockTrend(@Param("form") StockTrendForm stockTrendForm);
    List<MaterialCostPriceVO>getMaterialCostPriceTrend(@Param("form") StockTrendForm stockTrendForm);
    List<StockFlowVO>getStockFlowStatistics(@Param("form") StockTrendForm stockTrendForm);

}
