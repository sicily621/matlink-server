package com.clt.matlink.common.domain.vo;

import lombok.Data;

@Data
public class Result<T> {
    private static final int SUCCESS = 200;
    private static final int FAIL = 500;
    private static final String SUCCESS_STRING = "操作成功";
    private static final String FAIL_STRING = "操作失败";
    private int code;
    private String msg;
    private T data;
    public static <T> Result<T> success(){
        return buildResult(null, SUCCESS, SUCCESS_STRING);
    }
    public static <T> Result<T> success(T data){
        return buildResult(data, SUCCESS, SUCCESS_STRING);
    }

    public static <T> Result<T> fail(){
        return buildResult(null, FAIL, FAIL_STRING);
    }
    public static <T> Result<T> fail(String msg){
        return buildResult(null, FAIL, msg);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return buildResult(null, code, msg);
    }
    private static <T> Result<T> buildResult(T data, int code, String msg) {
        Result<T> objectResult = new Result<>();
        objectResult.setCode(code);
        objectResult.setMsg(msg);
        objectResult.setData(data);
        return objectResult;
    }
}
