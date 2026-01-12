package com.clt.matlink.modules.base.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_material_unit")
public class Unit extends BaseEntity {
    private Long id;
    private Long parentId;
    private String cnname;
    private String enname;
    private String unitSymbol;
    private String description;
}
