package com.mall.dao;

import com.mall.pojo.Cart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cart record);

    int insertSelective(Cart record);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    Cart selectByUserIdAndProductId(@Param("userId") int userId,
                                    @Param("productId") int productId);

    List<Cart> selectByUserId(int userId);

    int deleteByUserIdProductIds(@Param("userId") int userId,
                                 @Param("productIdList") List<String> productIdList);

    /**checkedOrUnCheckedAllProduct
     * updateCheckedByUserId
     * 修改选中状态
     * @param userId
     * @param checked
     * @return
     */
    int updateCheckedByUserIdProductId(@Param("userId") int userId,
                                       @Param("productId") Integer productId,
                                     @Param("checked") int checked);

    int selectCartProductCount(int userId);
}