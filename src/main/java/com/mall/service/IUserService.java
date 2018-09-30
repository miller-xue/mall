package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.User;

/**
 * Created by miller on 2018/9/24
 */
public interface IUserService
{



    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return
     */
    ServerResponse<User> login(String username, String password);

    /**
     * 校验用户名是否存在
     * @param id id
     * @param username 用户名
     * @return true 存在, false 不存在
     */
    boolean checkUsernameExist(Integer id, String username);

    /**
     * 校验邮箱是否存在
     * @param id id
     * @param email 邮箱
     * @return true 存在, false 不存在
     */
    boolean checkEmailExist(Integer id, String email);

    /**
     * 注册
     * @param user 用户对象
     * @return
     */
    ServerResponse<String> register(User user);

    /**
     * 校验字段
     * @param str 值
     * @param type 类型 username or email
     * @return
     */
    ServerResponse<String> checkValid(String str, String type);

    /**
     * 根据用户名查询找回问题
     * @param username 用户名
     * @return
     */
    ServerResponse<String> selectQuestion(String username);

    /**
     * 校验
     * @param username
     * @param question
     * @param answer
     * @return
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 未登陆情况下重置密码
     * @param username 用户名
     * @param passwordNew 新密码
     * @param forgetToken token
     * @return
     */
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 在线用户修改密码
     * @param user 用户对象
     * @param passwordOld 老密码
     * @param passwordNew 新密码
     * @return
     */
    ServerResponse<String> resetPassword(User user, String passwordOld, String passwordNew);

    /**
     * 修改用户信息
     * @param user 用户对象
     * @return
     */
    ServerResponse<User> updateInformation(User user);

    ServerResponse<User> getInformation(int userId);
}
