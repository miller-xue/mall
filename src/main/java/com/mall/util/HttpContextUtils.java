package com.mall.util;

import com.mall.common.Const;
import com.mall.pojo.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by miller on 2018/10/6
 * http工具类 线程安全
 */
public class HttpContextUtils {

    /**
     * 获取当前线程的Request
     * @return
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取当前线程的Response
     * @return
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * 获取当前线程的Session对象
     * @return
     */
    public static HttpSession getHttpSession() {
        return getHttpServletRequest().getSession();
    }

    /**
     * 获取当前线程的登陆用户 未登录返回Null
     * @return
     */
    public static User getCurrentUser() {
        Object user = getHttpSession().getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return null;
        }
        return (User) user;
    }
}
