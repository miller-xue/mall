package com.mall.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by miller on 2018/10/3
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@NeedLogin
public @interface Admin {
}
