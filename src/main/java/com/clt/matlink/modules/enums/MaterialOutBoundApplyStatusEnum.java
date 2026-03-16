package com.clt.matlink.modules.enums;

import lombok.Getter;

/**
 * 物料审批状态枚举
 */

@Getter
public enum MaterialOutBoundApplyStatusEnum {
    /**
     * 申请状态:0-未领料，1-已领料,2-废弃,3已归还
     */
    UN_OUT_BOUND(0, "未领料"),
    OUT_BOUND(1, "已领料"),
    ABANDON(2, "废弃"),
    RETURNED(3,"已归还");
    private final int status;
    private final String name;

    MaterialOutBoundApplyStatusEnum(int status,
                                    String name) {
        this.status = status;
        this.name = name;
    }

    public static MaterialOutBoundApplyStatusEnum get(int status) {
        for (MaterialOutBoundApplyStatusEnum c : MaterialOutBoundApplyStatusEnum.values()) {
            if (c.getStatus() == status) {
                return c;
            }
        }
        return null;
    }

    // 普通方法
    public static String getName(int status) {
        MaterialOutBoundApplyStatusEnum resourceTypeEnum = get(status);
        if(resourceTypeEnum == null){
            return null;
        }
        return resourceTypeEnum.getName();
    }

}
