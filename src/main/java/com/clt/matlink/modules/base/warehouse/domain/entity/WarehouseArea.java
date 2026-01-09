package com.clt.matlink.modules.base.warehouse.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("hg_warehouse_area")
public class WarehouseArea  extends BaseEntity {
    private Long id;
    private Long warehouseId;
    private String name;
    private Integer type;
}
