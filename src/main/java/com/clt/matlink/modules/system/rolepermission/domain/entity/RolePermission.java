package com.clt.matlink.modules.system.rolepermission.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.clt.matlink.common.domain.entity.BaseEntity;
import lombok.Data;
@Data
@TableName("clt_role_permission")
public class RolePermission  extends BaseEntity {
    private Long id;
    private Long roleId;
    private Long permissionId;
}
