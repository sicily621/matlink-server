package com.clt.matlink.modules.base.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_material_stock_type")
public class Stock extends BaseEntity {
    private Long id;
    private String name;
    private Long parentId;
    private Long typeId;
    private String description;
    private Long createUserId;
}
