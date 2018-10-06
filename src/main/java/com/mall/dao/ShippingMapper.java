package com.mall.dao;

import com.mall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);


    int deleteByPrimaryKeyUAndUserId(@Param("id") Integer id,
                                     @Param("userId") int userId);

    int updateByPrimaryKeyAndUserId(Shipping record);

    Shipping selectByPrimaryKeyAndUserId(@Param("id") Integer id,
                                         @Param("userId") int userId);

    List<Shipping> selectByUserId(int userId);
}