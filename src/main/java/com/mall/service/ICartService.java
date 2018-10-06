package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.vo.CartVo;

/**
 * Created by miller on 2018/10/4
 */
public interface ICartService {
    /**
     * 新增一个购物车
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> add(int userId, Integer productId, Integer count);


    /**
     * 修改购物车商品数量
     * @param userId
     * @param productId
     * @param count
     * @return
     */
    ServerResponse<CartVo> update(int userId, Integer productId, Integer count);

    /**
     * 删除购物车商品
     * @param userId
     * @param productIds
     * @return
     */
    ServerResponse<CartVo> deleteProduct(int userId, String productIds);

    /**
     * 购物车列表
     * @param userId
     * @return
     */
    ServerResponse<CartVo> list(int userId);

    /**
     * 选中or未选中所有
     * @param userId
     * @param checked
     * @return
     */
    ServerResponse<CartVo> selectOrUnSelectAll(int userId, int checked);

    /**
     * 选中未选中单个
     * @param userId
     * @param productId
     * @param checked
     * @return
     */
    ServerResponse<CartVo> selectOrUnSelect(int userId, Integer productId, int checked);

    /**
     * 获取总数量
     * @param userId
     * @return
     */
    ServerResponse<Integer> getCartProductCount(Integer userId);
}
