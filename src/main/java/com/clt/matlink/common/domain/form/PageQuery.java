package com.clt.matlink.common.domain.form;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class PageQuery implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer size = Integer.MAX_VALUE;
    private Integer currentPage = 1;


    public <T> Page<T> build() {
        Page<T> page = new Page<>(currentPage, size);
        return page;
    }
}
