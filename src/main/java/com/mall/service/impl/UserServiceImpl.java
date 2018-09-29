package com.mall.service.impl;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.common.TokenCatch;
import com.mall.dao.UserMapper;
import com.mall.pojo.User;
import com.mall.service.IUserService;
import com.mall.util.MD5Util;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

        return ServerResponse.buildSuccess("登陆成功", user);
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
            } else {
                if (checkEmailExist(str)) {
                    return ServerResponse.buildFail("邮箱已存在");
                }
            }
        } else {
            return ServerResponse.buildFail("参数错误");
        }
        return ServerResponse.buildSuccess("校验成功");
    }

    /**
     * 找回注册问题
     * @param username
     * @return
     */
    @Override
    public ServerResponse selectQuestion(String username) {
        boolean b = checkUsernameExist(username);
        if (!b) {
            return ServerResponse.buildFail("用户不存在");
        }
        String question = userMapper.selectQuestionByUsername(username);
        if (StringUtils.isNotBlank(question)) {
            return ServerResponse.buildSuccess(question);
        }
        return ServerResponse.buildFail("找回密码问题为空");
    }


    /**
     * 校验答案
     * @param username 用户名
     * @param question 问题
     * @param answer 答案
     * @return
     */
    public ServerResponse<String> checkAnswer(String username, String question, String answer) {
        // 1.查找对象是否存在
        int resultCount = userMapper.checkAnswer(username, question, answer);

        if (resultCount > 0) {
            // 说名问题及问题答案是这个用户的,并且是正确的
            String forgetToken = UUID.randomUUID().toString();
            // 生成Token 放入缓存中
            TokenCatch.setKey(TokenCatch.TOKEN_PREFIX + username, forgetToken);
            return ServerResponse.buildSuccess(forgetToken);
        }
        // 问题错误
        return ServerResponse.buildFail("问题答案错误");
    }

    @Override
    public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken) {
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.buildFail("参数错误,token需要传递");
        }
        if (!checkUsernameExist(username)) {
            return ServerResponse.buildFail("用户不存在");
        }

        String token = TokenCatch.getKey(TokenCatch.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.buildFail("token过期");
        }

        if (StringUtils.equals(token, forgetToken)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePassword(username, md5Password);
            if (rowCount > 0) {
                return ServerResponse.buildSuccess("修改密码成功");
            }else {
                return ServerResponse.buildFail("修改密码失败");
            }
        }else {
            return ServerResponse.buildFail("token错误,请重新获取");
        }
    }
}
