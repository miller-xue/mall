package com.mall.common;

import lombok.Getter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Created by miller on 2018/9/24
 */

@Getter
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
// 包装序列化json的时候,如果是null的对象,key也会消失
public class ServerResponse<T> {

    private int status;

    private String msg;

    private T data;

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public static <T> ServerResponse<T> buildSuccess() {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }


    public static <T> ServerResponse<T> buildSuccess(T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), data);
    }


    public static <T> ServerResponse<T> buildSuccessMsg(String msg) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg);
    }



    public static <T> ServerResponse<T> buildSuccess(String msg, T data) {
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(), msg, data);
    }


    public static <T> ServerResponse<T> buildFail() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode());
    }

    public static <T> ServerResponse<T> buildFail(String errorMsg) {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMsg);
    }

    public static <T> ServerResponse<T> buildFail(int errorCode, String errorMsg) {
        return new ServerResponse<T>(errorCode, errorMsg);
    }

}
