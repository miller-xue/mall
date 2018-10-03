package com.mall.common.annotation;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import com.mall.util.SysUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Created by miller on 2018/10/3
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private IUserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         if (handler instanceof HandlerMethod)
        {
            HandlerMethod hm = (HandlerMethod) handler;
            Admin admin = hm.getMethodAnnotation(Admin.class);
            if (admin == null)
            {
                return true;
            }
            // 1.取出用户
            User user = (User) request.getSession().getAttribute(Const.CURRENT_USER);

            if (userService.isAdmin(user).isSuccess())
            {
                return true;
            }
            else
            {
                SysUtil.render(response, ServerResponse.buildFail("无权限操作,需要管理员权限"));
                return false;
            }
        }
        return true;
    }
}
