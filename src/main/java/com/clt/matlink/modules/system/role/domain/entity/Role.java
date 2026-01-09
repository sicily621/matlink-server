package com.clt.matlink.modules.system.role.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_role")
public class Role extends BaseEntity {
    private Long id;
    private String name;
    private String code;
    private String description;
    private Integer status;
}
