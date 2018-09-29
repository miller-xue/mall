package com.mall.controller.protal;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by miller on 2018/9/18
 * @author Miller
 */
@Controller
@RequestMapping(value = "/user/")
public class UserController
{

    @Autowired
    private IUserService userService;

    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login.do" , method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session)
    {
        ServerResponse<User> response = userService.login(username, password);
        if (response.isSuccess())
        {
            session.setAttribute(Const.CURRENT_USER, response.getData());
        }
        return response;
    }

    /**
     * 登出
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout.do",method = RequestMethod.GET)
    public ServerResponse<String> logout(HttpSession session)
    {
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.buildSuccess();
    }

    /**
     * 注册一个用户
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "register.do" , method = RequestMethod.POST)
    public ServerResponse<String> register(User user)
    {
        return userService.register(user);
    }

    /**
     * 用户参数校验
     * @param str 值
     * @param type 类型, 用户名or邮箱
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "check_valid.do" , method = RequestMethod.GET)
    public ServerResponse<String> checkValid(String str, String type)
    {
        return userService.checkValid(str, type);
    }

    /**
     * 获取当前登陆用户信息
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getUserInfo.do",method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(HttpSession session) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null)
        {
            return ServerResponse.buildFail("用户未登陆,无法获取当前用户信息");
        }
        return ServerResponse.buildSuccess(user);
    }

    /**
     * 根据用户名称找到注册提示问题
     * @param username
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/forget_get_question.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetGetQuestion(String username) {
        return userService.selectQuestion(username);
    }

    /**
     * 校验密码问题答案
     * @param username 用户名
     * @param question 问题
     * @param answer 答案
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/forget_check_answer.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetCheckAnswer(String username, String question, String answer) {
        return userService.checkAnswer(username, question, answer);
    }

    /**
     * 重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/forget_reset_password.do",method = RequestMethod.GET)
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        return userService.forgetResetPassword(username, passwordNew, forgetToken);
    }
}
