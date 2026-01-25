package com.clt.matlink.modules.instock.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
@TableName("clt_material_in_stock_file")
public class InStockFile extends BaseEntity {
    private Long id;
    private Long inStockId;
    private String filePath;
    private String fileName;
}
