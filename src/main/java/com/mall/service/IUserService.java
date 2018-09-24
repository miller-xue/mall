package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.User;

/**
 * Created by miller on 2018/9/24
 */
public interface IUserService {

    ServerResponse<User> login(String username, String password);

    boolean checkUsernameExist(String username);
}
