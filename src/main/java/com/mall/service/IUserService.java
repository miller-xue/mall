package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.User;

/**
 * Created by miller on 2018/9/24
 */
public interface IUserService
{

    ServerResponse<User> login(String username, String password);

    boolean checkUsernameExist(String username);

    boolean checkEmailExist(String email);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str, String type);

    ServerResponse<String> selectQuestion(String username);

    ServerResponse<String> checkAnswer(String username, String question, String answer);

    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);
}
