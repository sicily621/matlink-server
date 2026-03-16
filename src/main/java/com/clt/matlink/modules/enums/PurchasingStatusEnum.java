package com.clt.matlink.modules.enums;

import lombok.Getter;

/**
 * 物料审批状态枚举
 */

@Getter
public enum PurchasingStatusEnum {
    /**
     * @Schema(description = "采购状态：0未采购1已采购2已废弃3已入库")
     */
    UN_PURCHASED(0, "未采购"),
    PURCHASED(1, "已采购"),
    ABANDONED(2, "已废弃"),
    IN_STOCK(3, "已入库"),
    RETURNED(4,"已归还"),
    ;
    private final int status;
    private final String name;

    PurchasingStatusEnum(int status,
                         String name) {
        this.status = status;
        this.name = name;
    }

    public static PurchasingStatusEnum get(int status) {
        for (PurchasingStatusEnum c : PurchasingStatusEnum.values()) {
            if (c.getStatus() == status) {
                return c;
            }
        }
        return null;
    }

    // 普通方法
    public static String getName(int status) {
        PurchasingStatusEnum resourceTypeEnum = get(status);
        if(resourceTypeEnum == null){
            return null;
        }
        return resourceTypeEnum.getName();
    }

}
