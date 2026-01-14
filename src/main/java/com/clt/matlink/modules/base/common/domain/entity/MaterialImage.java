package com.clt.matlink.modules.base.common.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_material_image")
public class MaterialImage extends BaseEntity {
    private Long id;
    private Long materialId;
    private String imagePath;
}
