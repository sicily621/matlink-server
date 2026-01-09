package com.clt.matlink.common.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class PageInfo <T>{
    private Long total;
    private List<T> list;
    public static <T> PageInfo<T> build(IPage<T> page) {
        PageInfo<T> rspData = new PageInfo<>();
        rspData.setList(page.getRecords());
        rspData.setTotal(page.getTotal());
        return rspData;
    }
}
