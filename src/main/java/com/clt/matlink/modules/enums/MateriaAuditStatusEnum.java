package com.clt.matlink.modules.enums;

import lombok.Getter;

/**
 * 物料审批状态枚举
 */

@Getter
public enum MateriaAuditStatusEnum {

    AUDIT_AWAIT(0, "待审批"),
    AUDIT_FAIL(1, "审批拒绝"),
    AUDIT_SUCCESS(2, "审批通过"),
    AUDIT_BEING(3, "审批中"),
    ;
    private final int status;
    private final String name;

    MateriaAuditStatusEnum(int status,
                           String name) {
        this.status = status;
        this.name = name;
    }

    public static MateriaAuditStatusEnum get(int status) {
        for (MateriaAuditStatusEnum c : MateriaAuditStatusEnum.values()) {
            if (c.getStatus() == status) {
                return c;
            }
        }
        return null;
    }

    // 普通方法
    public static String getName(int status) {
        MateriaAuditStatusEnum resourceTypeEnum = get(status);
        if(resourceTypeEnum == null){
            return null;
        }
        return resourceTypeEnum.getName();
    }

}
