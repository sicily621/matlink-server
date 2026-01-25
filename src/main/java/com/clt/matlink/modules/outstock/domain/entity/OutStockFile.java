package com.clt.matlink.modules.outstock.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_material_out_stock_file")
public class OutStockFile extends BaseEntity {
    private Long id;
    private Long outStockId;
    private String filePath;
    private String fileName;
}
