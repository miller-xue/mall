package com.mall.service.impl;

import com.mall.common.ServerResponse;
import com.mall.dao.UserMapper;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by miller on 2018/9/24
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public ServerResponse<User> login(String username, String password) {
        if (!checkUsernameExist(username)) {
            return ServerResponse.buildFail("用户名不存在");
        }
        // 密码登陆MD5 TODO
        User user = userMapper.selectLogin(username, password);
        if (user == null) {
            return ServerResponse.buildFail("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.<User>buildSuccess("登陆成功" , user);
    }


    @Override
    public boolean checkUsernameExist(String username) {
        return userMapper.countByUserName(username) > 0;
    }


}
