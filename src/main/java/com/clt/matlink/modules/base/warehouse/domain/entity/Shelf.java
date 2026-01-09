package com.clt.matlink.modules.base.warehouse.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_shelf")
public class Shelf extends BaseEntity {
    private Long id;
    private Long areaId;
    private String code;
    private String name;
    private Integer layerCount;
    private BigDecimal maxCapacity;
}
