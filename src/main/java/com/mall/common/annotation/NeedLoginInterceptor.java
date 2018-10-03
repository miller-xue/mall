package com.mall.common.annotation;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.pojo.User;
import com.mall.util.SysUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by miller on 2018/10/3
 */
public class NeedLoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod)
        {
            HandlerMethod hm = (HandlerMethod) handler;
            NeedLogin needLogin = hm.getMethodAnnotation(NeedLogin.class);
            if (needLogin == null)
            {
                return true;
            }
            User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);
            if (user == null)
            {
                SysUtil.render(response, ServerResponse.buildFail(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆"));
                return false;
            }
        }
        return true;
    }

}
