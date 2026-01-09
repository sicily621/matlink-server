package com.clt.matlink.modules.base.warehouse.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("hg_warehouse")
public class Warehouse extends BaseEntity {
    private Long id;
    private String name;
    private String code;
    private BigDecimal area;
    private Long managerId;
}
