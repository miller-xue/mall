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
        if (!checkUsernameExist(null,username)) {
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
    public boolean checkUsernameExist(Integer id, String username) {
        return userMapper.countByUserName(username,id) > 0;
    }

    public boolean checkEmailExist(Integer id, String email) {
        return userMapper.countByEmail(email,id) > 0;
    }




    public ServerResponse<String> register(User user) {
        if (checkUsernameExist(null,user.getUsername())) {
            return ServerResponse.buildFail("用户名已存在");
        }
        if (checkEmailExist(null,user.getEmail())) {
            return ServerResponse.buildFail("邮箱已存在");
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        // md5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));
        int insert = userMapper.insert(user);
        if (insert == 0) {
            return ServerResponse.buildFail("注册失败");
        }
        return ServerResponse.buildSuccessMsg("注册成功");
    }

    public ServerResponse<String> checkValid(String str, String type) {
        if (StringUtils.isNotBlank(type)) {
            if (Const.USERNAME.equals(type)) {
                if (checkUsernameExist(null,str)) {
                    return ServerResponse.buildFail("用户名已存在");
                }
            } else {
                if (checkEmailExist(null,str)) {
                    return ServerResponse.buildFail("邮箱已存在");
                }
            }
        } else {
            return ServerResponse.buildFail("参数错误");
        }
        return ServerResponse.buildSuccessMsg("校验成功");
    }

    /**
     * 找回注册问题
     * @param username
     * @return
     */
    @Override
    public ServerResponse selectQuestion(String username) {
        boolean b = checkUsernameExist(null,username);
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
        // 1.参数校验
        if (StringUtils.isBlank(forgetToken)) {
            return ServerResponse.buildFail("参数错误,token需要传递");
        }
        if (!checkUsernameExist(null, username)) {
            return ServerResponse.buildFail("用户不存在");
        }
        // 2.取出Token
        String token = TokenCatch.getKey(TokenCatch.TOKEN_PREFIX + username);
        if (StringUtils.isBlank(token)) {
            return ServerResponse.buildFail("token过期");
        }

        if (StringUtils.equals(token, forgetToken)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePassword(username, md5Password);
            if (rowCount > 0) {
                // TODO 删除Token
                return ServerResponse.buildSuccessMsg("修改密码成功");
            } else {
                return ServerResponse.buildFail("修改密码失败");
            }
        } else {
            return ServerResponse.buildFail("token错误,请重新获取");
        }
    }

    @Override
    public ServerResponse<String> resetPassword(User user, String passwordOld, String passwordNew)
    {
        // 防止横向越权,要校验这个用户的旧密码,一定要指定是这用户
        int result = userMapper.countByPasswordAndId(MD5Util.MD5EncodeUtf8(passwordOld), user.getId());
        if (result == 0)
        {
            return ServerResponse.buildFail("旧密码错误");
        }

        String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);

        int rowCount = userMapper.updatePassword(user.getUsername(), md5Password);
        if (rowCount > 0)
        {
            return ServerResponse.buildSuccessMsg("修改密码成功");
        }
        return ServerResponse.buildFail("修改密码失败");
    }


    public ServerResponse<User> updateInformation(User user) {
        // username 不能被更新
        // email 也要进行校验,新的
        boolean checkEmailExist = checkEmailExist(user.getId(), user.getEmail());
        if (checkEmailExist) {
            return ServerResponse.buildFail("邮箱已存在");
        }

        User updateUser = new User();
        updateUser.setEmail(user.getEmail());
        updateUser.setId(user.getId());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());

        int updateCount = userMapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0)
        {
            return ServerResponse.buildSuccess("修改用户成功",updateUser);
        }
        return ServerResponse.buildFail("修改用户失败");
    }

    @Override
    public ServerResponse<User> getInformation(int userId) {
        User user = userMapper.selectByPrimaryKey(userId);
        if(user == null){
            return ServerResponse.buildFail("找不到当前用户");
        }
        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.buildSuccess(user);
    }

    @Override
    public ServerResponse isAdmin(User user) {
        if (user != null && user.getRole().intValue() == Const.Role.ROLE_ADMIN) {
            return ServerResponse.buildSuccess();
        }
        return ServerResponse.buildFail();
    }
}
