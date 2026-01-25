package com.clt.matlink.modules.base.common.service.impl;

import cn.hutool.core.collection.CollStreamUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.form.PageQuery;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.base.common.domain.entity.MaterialImage;
import com.clt.matlink.modules.base.common.domain.form.MaterialImageForm;
import com.clt.matlink.modules.base.common.mapper.MaterialImageMapper;
import com.clt.matlink.modules.base.common.service.MaterialImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaterialImageServiceImpl implements MaterialImageService {
    @Autowired
    private MaterialImageMapper materialImageMapper;

    @Override
    public MaterialImage save(MaterialImage materialImage) {
        int flag = 0;
        if(materialImage.getId()==null){
            flag= materialImageMapper.insert(materialImage);
        }else{
            flag = materialImageMapper.updateById(materialImage);
        }
        if(flag>0){
            return materialImageMapper.selectById(materialImage.getId());
        }else{
            return null;
        }

    }

    @Override
    public MaterialImage getById(Long id) {
        return materialImageMapper.selectById(id);
    }

    @Override
    public List<MaterialImage> getByIds(List<Long> ids) {
        LambdaQueryWrapper<MaterialImage> lqw = Wrappers.lambdaQuery();
        lqw.eq(MaterialImage::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( MaterialImage::getId, ids);
        return materialImageMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        materialImageMapper.deleteById(id);
        return true;
    }

    @Override
    public List<MaterialImage> list(MaterialImageForm materialImageForm) {
        LambdaQueryWrapper<MaterialImage> lqw = getQueryWrapper(materialImageForm);
        return materialImageMapper.selectList(lqw);
    }



    @Override
    public PageInfo<MaterialImage> page(MaterialImageForm materialImageForm, PageQuery pageQuery) {
        LambdaQueryWrapper<MaterialImage> lqw = getQueryWrapper(materialImageForm);
        Page<MaterialImage> page = pageQuery.build();
        Page<MaterialImage> result = materialImageMapper.selectPage(page, lqw);
        PageInfo<MaterialImage> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<MaterialImage> batchSave(List<MaterialImage> materialImages) {
        materialImageMapper.insertOrUpdateBatch(materialImages);
        List<Long> list = CollStreamUtil.toList(materialImages,MaterialImage::getId);
        List<MaterialImage> result = getByIds(list);
        return result;
    }

    private LambdaQueryWrapper<MaterialImage> getQueryWrapper(MaterialImageForm materialImageForm) {

        LambdaQueryWrapper<MaterialImage> lqw = Wrappers.lambdaQuery();
        lqw.eq(materialImageForm.getMaterialId()!=null, MaterialImage::getMaterialId, materialImageForm.getMaterialId());
        lqw.in(CollUtil.isNotEmpty(materialImageForm.getMaterialIds()), MaterialImage::getMaterialId, materialImageForm.getMaterialIds());
        lqw.eq( MaterialImage::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return lqw;
    }
}
