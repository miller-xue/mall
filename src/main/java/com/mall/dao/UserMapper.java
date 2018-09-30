package com.mall.dao;

import com.mall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int countByUserName(@Param("username") String username,
                        @Param("id") Integer id);


    int countByEmail(@Param("email") String email,
                     @Param("id") Integer id);

    User selectLogin(@Param("username") String username,
                     @Param("password") String password);

    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username,
                    @Param("question") String question,
                    @Param("answer") String answer);


    int updatePassword(@Param("username") String username,
                       @Param("password") String password);

    int countByPasswordAndId(@Param("password") String password,
                             @Param("id") Integer id);

}