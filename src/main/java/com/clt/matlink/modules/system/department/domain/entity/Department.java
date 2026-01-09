package com.clt.matlink.modules.system.department.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("clt_department")
public class Department extends BaseEntity {
    private Long id;
    private String name;
    private Long parentId;
    private Long managerId;

}
