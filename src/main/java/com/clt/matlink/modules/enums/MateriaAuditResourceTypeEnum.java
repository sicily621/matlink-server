package com.clt.matlink.modules.enums;

import lombok.Getter;

/**
 * 物料审核资源状态枚举
 */

@Getter
public enum MateriaAuditResourceTypeEnum {

    MATERIAL_IN_STOCK(1, "入库"),
    MATERIAL_OUT_STOCK(2, "出库"),
    MATERIAL_OUTBOUND(3, "领料"),
    MATERIAL_INVENTORY(4, "盘点"),
    PURCHASING(5, "采购"),
    ;
    private final int value;
    private final String name;

    MateriaAuditResourceTypeEnum(int value,
                                 String name) {
        this.value = value;
        this.name = name;
    }

    public static MateriaAuditResourceTypeEnum get(int value) {
        for (MateriaAuditResourceTypeEnum c : MateriaAuditResourceTypeEnum.values()) {
            if (c.getValue() == value) {
                return c;
            }
        }
        return null;
    }

    // 普通方法
    public static String getName(int value) {
        MateriaAuditResourceTypeEnum resourceTypeEnum = get(value);
        if(resourceTypeEnum == null){
            return null;
        }
        return resourceTypeEnum.getName();
    }

}
