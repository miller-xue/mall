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

    /**
     * 根据主键和用户id删除一个地址
     * @param id 主键
     * @param userId 用户id
     * @return
     */
    int deleteByPrimaryKeyAndUserId(@Param("id") Integer id,
                                     @Param("userId") int userId);

    /**
     * 根据主键和用户id修改对象
     * @param shipping 对象
     * @return
     */
    int updateByPrimaryKeyAndUserId(Shipping shipping);

    /**
     * 根据主键和用户Id查询一个对象
     * @param id id
     * @param userId 用户id
     * @return
     */
    Shipping selectByPrimaryKeyAndUserId(@Param("id") Integer id,
                                         @Param("userId") int userId);

    /**
     * 查询列表
     * @param userId 用户Id
     * @return
     */
    List<Shipping> selectByUserId(int userId);
}