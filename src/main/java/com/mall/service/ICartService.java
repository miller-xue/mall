package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.vo.CartVo;

/**
 * Created by miller on 2018/10/4
 */
public interface ICartService {
    ServerResponse<CartVo> add(int userId, Integer productId, Integer count);


    ServerResponse<CartVo> update(int userId, Integer productId, Integer count);


    ServerResponse<CartVo> deleteProduct(int userId, String productIds);

    ServerResponse<CartVo> list(int userId);

    ServerResponse<CartVo> selectOrUnSelectAll(int userId, int checked);

    ServerResponse<CartVo> selectOrUnSelect(int userId, Integer productId, int checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
