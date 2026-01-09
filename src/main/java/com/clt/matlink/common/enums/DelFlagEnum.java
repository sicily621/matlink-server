package com.clt.matlink.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态
 *
 * @author zm
 */
@Getter
@AllArgsConstructor
public enum DelFlagEnum implements BaseEnum {

    NORMAL(0, "正常"),
    DELETE(1, "删除");

    private final Integer value;
    private final String desc;

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
