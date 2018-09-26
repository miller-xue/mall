package com.mall.service.impl;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.dao.UserMapper;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import com.mall.util.MD5Util;
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
        // 密码登陆MD5
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectLogin(username, md5Password);
        if (user == null) {
            return ServerResponse.buildFail("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);

        return ServerResponse.buildSuccess("登陆成功" , user);
    }


    @Override
    public boolean checkUsernameExist(String username) {
        return userMapper.countByUserName(username) > 0;
    }

    public boolean checkEmailExist(String email) {
        return userMapper.countByUserName(email) > 0;
    }

    public ServerResponse<String> register(User user) {
        if (checkUsernameExist(user.getUsername())) {
            return ServerResponse.buildFail("用户名已存在");
        }
        if (checkEmailExist(user.getEmail())) {
            return ServerResponse.buildSuccess("邮箱已存在");
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        // md5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int insert = userMapper.insert(user);
        if (insert == 0) {
            return ServerResponse.buildFail("注册失败");
        }
        return ServerResponse.buildSuccess("注册成功");
    }

    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Const.EMAIL.equals(type)) {
                if (checkUsernameExist(str)) {
                    return ServerResponse.buildFail("用户名已存在");
                }
            }else {
                if (checkEmailExist(str)) {
                    return ServerResponse.buildFail("邮箱已存在");
                }
            }
        }else {
            return ServerResponse.buildFail("参数错误");
        }
        return ServerResponse.buildSuccess("校验成功");
    }

}
