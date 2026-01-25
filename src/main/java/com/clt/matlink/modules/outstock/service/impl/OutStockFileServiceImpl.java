package com.clt.matlink.modules.outstock.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.outstock.domain.entity.OutStockFile;
import com.clt.matlink.modules.outstock.domain.form.OutStockFileForm;
import com.clt.matlink.modules.outstock.mapper.OutStockFileMapper;
import com.clt.matlink.modules.outstock.service.OutStockFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OutStockFileServiceImpl implements OutStockFileService {
    @Autowired
    private OutStockFileMapper outStockFileMapper;
    @Override
    public OutStockFile save(OutStockFile outStockFile) {
        int flag = 0;
        if(outStockFile.getId()==null){
            flag= outStockFileMapper.insert(outStockFile);
        }else{
            flag = outStockFileMapper.updateById(outStockFile);
        }
        if(flag>0){
            return outStockFileMapper.selectById(outStockFile.getId());
        }else{
            return null;
        }

    }

    @Override
    public OutStockFile getById(Long id) {
        return outStockFileMapper.selectById(id);
    }

    @Override
    public List<OutStockFile> getByIds(List<Long> ids) {
        LambdaQueryWrapper<OutStockFile> lqw = Wrappers.lambdaQuery();
        lqw.eq(OutStockFile::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( OutStockFile::getId, ids);
        return outStockFileMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        outStockFileMapper.deleteById(id);
        return true;
    }

    @Override
    public List<OutStockFile> list(OutStockFileForm outStockFileForm) {
        LambdaQueryWrapper<OutStockFile> lqw = getQueryWrapper(outStockFileForm);
        return outStockFileMapper.selectList(lqw);
    }



    @Override
    public PageInfo<OutStockFile> page(OutStockFileForm outStockFileForm, PageQuery pageQuery) {
        LambdaQueryWrapper<OutStockFile> lqw = getQueryWrapper(outStockFileForm);
        Page<OutStockFile> page = pageQuery.build();
        Page<OutStockFile> result = outStockFileMapper.selectPage(page, lqw);
        PageInfo<OutStockFile> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<OutStockFile> batchSave(List<OutStockFile> inStockFiles) {
        outStockFileMapper.insertOrUpdateBatch(inStockFiles);
        List<Long> list = CollStreamUtil.toList(inStockFiles, OutStockFile::getId);
        List<OutStockFile> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<OutStockFile> getQueryWrapper(OutStockFileForm outStockFileForm) {

        LambdaQueryWrapper<OutStockFile> lqw = Wrappers.lambdaQuery();
        lqw.eq(outStockFileForm.getOutStockId()!=null, OutStockFile::getOutStockId, outStockFileForm.getOutStockId());
        lqw.in(CollUtil.isNotEmpty(outStockFileForm.getOutStockIds()), OutStockFile::getOutStockId, outStockFileForm.getOutStockIds());
        lqw.eq( OutStockFile::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
