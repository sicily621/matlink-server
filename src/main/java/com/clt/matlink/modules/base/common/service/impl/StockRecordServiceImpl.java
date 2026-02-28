package com.clt.matlink.modules.base.common.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.base.common.domain.entity.StockRecord;
import com.clt.matlink.modules.base.common.domain.form.StockRecordForm;
import com.clt.matlink.modules.base.common.mapper.StockRecordMapper;
import com.clt.matlink.modules.base.common.service.StockRecordService;
import com.clt.matlink.modules.outstock.domain.entity.OutStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
