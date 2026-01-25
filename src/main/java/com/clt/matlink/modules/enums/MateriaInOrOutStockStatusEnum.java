package com.clt.matlink.modules.enums;

import lombok.Getter;

@Getter
public enum MateriaInOrOutStockStatusEnum {

    NOT_IN_OR_OUT_STOCK(0, "未出入库"),

    IN_OR_OUT_STOCK(1, "已出入库"),

    CANCELED(2, "已作废")
    ;
    private final int value;
    private final String name;

    MateriaInOrOutStockStatusEnum(int value,
                                  String name) {
        this.value = value;
        this.name = name;
    }

    public static MateriaInOrOutStockStatusEnum get(int value) {
        for (MateriaInOrOutStockStatusEnum c : MateriaInOrOutStockStatusEnum.values()) {
            if (c.getValue() == value) {
                return c;
            }
        }
        return null;
    }

    // 普通方法
    public static String getName(int value) {
        MateriaInOrOutStockStatusEnum resourceTypeEnum = get(value);
        if(resourceTypeEnum == null){
            return null;
        }
        return resourceTypeEnum.getName();
    }

}
