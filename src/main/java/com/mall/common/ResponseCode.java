package com.mall.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by miller on 2018/9/24
 *
 * @author Miller
 */
@AllArgsConstructor
@Getter
public enum ResponseCode {

    /**
     * 请求成功
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * 系统错误
     */
    ERROR(1, "ERROR"),
    /**
     * 需要登陆
     */
    NEED_LOGIN(10, "NEED_LOGIN"),
    /**
     * 参数错误
     */
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT"),

    ;

    private final int code;
    private final String desc;


}
