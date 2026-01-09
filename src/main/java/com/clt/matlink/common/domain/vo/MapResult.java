package com.clt.matlink.common.domain.vo;

import cn.hutool.core.util.ObjectUtil;

import java.util.HashMap;

/**
 * 操作消息提醒
 */
public class MapResult extends HashMap<String, Object> {

    private static final int SUCCESS = 200;
    private static final int FAIL = 500;

    /**
     * 状态码
     */
    public static final String CODE_TAG = "code";
    /**
     * 返回内容
     */
    public static final String MSG_TAG = "msg";
    /**
     * 数据对象
     */
    public static final String DATA_TAG = "data";
    private static final long serialVersionUID = 1L;

    /**
     * 初始化一个新创建的 MapResult 对象，使其表示一个空消息。
     */
    public MapResult() {
    }

    /**
     * 初始化一个新创建的 MapResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     */
    public MapResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 MapResult 对象
     *
     * @param code 状态码
     * @param msg  返回内容
     * @param data 数据对象
     */
    public MapResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (ObjectUtil.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static MapResult success() {
        return MapResult.success("操作成功");
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
    public static MapResult success(Object data) {
        return MapResult.success("操作成功", data);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static MapResult success(String msg) {
        return MapResult.success(msg, null);
    }

    /**
     * 返回成功消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static MapResult success(String msg, Object data) {
        return new MapResult(SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static MapResult error() {
        return MapResult.error("操作失败");
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static MapResult error(String msg) {
        return MapResult.error(msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param msg  返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static MapResult error(String msg, Object data) {
        return new MapResult(FAIL, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg  返回内容
     * @return 警告消息
     */
    public static MapResult error(int code, String msg) {
        return new MapResult(code, msg, null);
    }

    /**
     * 方便链式调用
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public MapResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
