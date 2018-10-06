package com.mall.service.impl;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.common.SysConfig;
import com.mall.dao.CartMapper;
import com.mall.pojo.Cart;
import com.mall.service.ICartService;
import com.mall.service.IProductService;
import com.mall.util.BigDecimalUtil;
import com.mall.vo.CartProductVo;
import com.mall.vo.CartVo;
import com.mall.vo.ProductDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by miller on 2018/10/4
 */
@Slf4j
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private IProductService productService;

    public ServerResponse<CartVo> add(int userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.buildFail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        // 校验productId是否存在


        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart == null) {
            //这个产品不在购物车里,需要新增一个产品记录
            Cart build = Cart.builder().quantity(count).checked(Const.Cart.CHECKED).productId(productId).userId(userId).build();
            cartMapper.insert(build);
        }else {
            //这个产品已存在购物车里了
            //如果产品已存在,数量相加
            cart.setQuantity(cart.getQuantity() + count);
            cartMapper.updateByPrimaryKey(cart);
        }
        return this.list(userId);
    }

    public ServerResponse<CartVo> update(int userId, Integer productId, Integer count) {
        if (productId == null || count == null) {
            return ServerResponse.buildFail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        if (cart != null) {
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKey(cart);
        }
        return this.list(userId);
    }


    public ServerResponse<CartVo> deleteProduct(int userId, String productIds) {
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        if (productIds == null) {
            return ServerResponse.buildFail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        if (CollectionUtils.isEmpty(productIdList)) {
            return ServerResponse.buildFail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        cartMapper.deleteByUserIdProductIds(userId, productIdList);

        return this.list(userId);
    }


    public ServerResponse<CartVo> selectOrUnSelectAll(int userId,int checked) {
        cartMapper.updateCheckedByUserIdProductId(userId,null, checked);
        return this.list(userId);
    }

    public ServerResponse<CartVo> selectOrUnSelect(int userId, Integer productId, int checked) {
        if (productId != null) {
            cartMapper.updateCheckedByUserIdProductId(userId, productId, checked);
        }
        return this.list(userId);
    }

    public ServerResponse<Integer> getCartProductCount(Integer userId) {
        //TODO 参数校验放在服务层来做
        if (userId == null) {
            return ServerResponse.buildSuccess(0);
        }
        return ServerResponse.buildSuccess(cartMapper.selectCartProductCount(userId));
    }


    public ServerResponse<CartVo> list(int userId) {
        return ServerResponse.buildSuccess(getCartVo(userId));
    }

    @Transactional
    public CartVo getCartVo(int userId) {
        List<Cart> cartList = cartMapper.selectByUserId(userId);
        BigDecimal cartTotalPrice = BigDecimalUtil.ZERO;
        Boolean allChecked = true;

        List<CartProductVo> cartProductVoList = new ArrayList<>(cartList.size());
        if (CollectionUtils.isNotEmpty(cartList)) {
            for (Cart cart : cartList) {
                ProductDetailVo product = productService.getProductDetail(cart.getProductId()).getData();
                CartProductVo cartProductVo = CartProductVo.builder().id(cart.getId())
                        .userId(cart.getUserId())
                        .productId(cart.getProductId()).build();
                if (product != null) {
                    cartProductVo.setProductMainImage(product.getMainImage());
                    cartProductVo.setProductName(product.getName());
                    cartProductVo.setProductSubtitle(product.getSubtitle());
                    cartProductVo.setProductStatus(product.getStatus());
                    cartProductVo.setProductPrice(product.getPrice());
                    cartProductVo.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStock() >= cart.getQuantity()) {
                        buyLimitCount = cart.getQuantity();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVo.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        cartMapper.updateByPrimaryKeySelective(Cart.builder().id(cart.getId()).quantity(buyLimitCount).build());
                    }
                    cartProductVo.setQuantity(buyLimitCount);
                    cartProductVo.setProductTotalPrice(BigDecimalUtil.mul(buyLimitCount, product.getPrice().doubleValue()));
                    cartProductVo.setProductChecked(cart.getChecked());
                }
                if (cart.getChecked() == Const.Cart.CHECKED) {
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(), cartProductVo.getProductTotalPrice().doubleValue());
                }else {
                    allChecked = false;
                }
                cartProductVoList.add(cartProductVo);
            }
        }

        return CartVo.builder().allChecked(allChecked).
                cartProductVoList(cartProductVoList).
                cartTotalPrice(cartTotalPrice).
                imageHost(SysConfig.ftpServerHttpPrefix).build();

    }
}
