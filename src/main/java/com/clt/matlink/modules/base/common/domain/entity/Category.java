package com.clt.matlink.modules.base.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_material_category")
public class Category extends BaseEntity {
    private Long id;
    private String name;
    private String code;
    private Long parentId;
}
