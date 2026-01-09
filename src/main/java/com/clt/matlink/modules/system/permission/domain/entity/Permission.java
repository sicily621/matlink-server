package com.clt.matlink.modules.system.permission.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_permission")
public class Permission extends BaseEntity {
    private Long id;
    private String name;
    private String moduleCode;
    private Long parentId;
    private String url;
    private String description;
    private Integer action;
    private Integer type;
}
