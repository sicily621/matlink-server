package com.clt.matlink.modules.instock.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.base.common.service.CategoryService;
import com.clt.matlink.modules.instock.domain.entity.InStockFile;
import com.clt.matlink.modules.instock.domain.form.InStockFileForm;
import com.clt.matlink.modules.instock.mapper.InStockFileMapper;
import com.clt.matlink.modules.instock.service.InStockFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InStockFileServiceImpl implements InStockFileService {
    @Autowired
    private InStockFileMapper inStockFileMapper;
    @Override
    public InStockFile save(InStockFile inStockFile) {
        int flag = 0;
        if(inStockFile.getId()==null){
            flag= inStockFileMapper.insert(inStockFile);
        }else{
            flag = inStockFileMapper.updateById(inStockFile);
        }
        if(flag>0){
            return inStockFileMapper.selectById(inStockFile.getId());
        }else{
            return null;
        }

    }

    @Override
    public InStockFile getById(Long id) {
        return inStockFileMapper.selectById(id);
    }

    @Override
    public List<InStockFile> getByIds(List<Long> ids) {
        LambdaQueryWrapper<InStockFile> lqw = Wrappers.lambdaQuery();
        lqw.eq(InStockFile::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( InStockFile::getId, ids);
        return inStockFileMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        inStockFileMapper.deleteById(id);
        return true;
    }

    @Override
    public List<InStockFile> list(InStockFileForm inStockFileForm) {
        LambdaQueryWrapper<InStockFile> lqw = getQueryWrapper(inStockFileForm);
        return inStockFileMapper.selectList(lqw);
    }



    @Override
    public PageInfo<InStockFile> page(InStockFileForm inStockFileForm, PageQuery pageQuery) {
        LambdaQueryWrapper<InStockFile> lqw = getQueryWrapper(inStockFileForm);
        Page<InStockFile> page = pageQuery.build();
        Page<InStockFile> result = inStockFileMapper.selectPage(page, lqw);
        PageInfo<InStockFile> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<InStockFile> batchSave(List<InStockFile> inStockFiles) {
        inStockFileMapper.insertOrUpdateBatch(inStockFiles);
        List<Long> list = CollStreamUtil.toList(inStockFiles,InStockFile::getId);
        List<InStockFile> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<InStockFile> getQueryWrapper(InStockFileForm inStockFileForm) {

        LambdaQueryWrapper<InStockFile> lqw = Wrappers.lambdaQuery();
        lqw.eq(inStockFileForm.getInStockId()!=null, InStockFile::getInStockId, inStockFileForm.getInStockId());
        lqw.in(CollUtil.isNotEmpty(inStockFileForm.getInStockIds()), InStockFile::getInStockId, inStockFileForm.getInStockIds());
        lqw.eq( InStockFile::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
