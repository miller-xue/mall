package com.mall.controller.protal;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.common.annotation.NeedLogin;
import com.mall.pojo.User;
import com.mall.service.ICartService;
import com.mall.util.HttpContextUtils;
import com.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by miller on 2018/10/4
 */
@Controller
@RequestMapping(value = "/cart/")
public class CartController {

    @Autowired
    private ICartService cartService;


    /**
     * 新增一个购物车
     * @param productId
     * @param count
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "add.do")
    public ServerResponse<CartVo> add(Integer productId, Integer count) {
        User currentUser = HttpContextUtils.getCurrentUser();
        return cartService.add(currentUser.getId(), productId, count);
    }

    /**
     * 修改购物车数量
     * @param productId
     * @param count
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "update.do")
    public ServerResponse<CartVo> update( Integer productId, Integer count) {
        User currentUser = HttpContextUtils.getCurrentUser();
        return cartService.update(currentUser.getId(), productId, count);
    }

    /**
     * 删除一个购物车对象
     * @param productIds
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "delete_product.do")
    public ServerResponse<CartVo> deleteProduct( String productIds) {
        User currentUser = HttpContextUtils.getCurrentUser();
        return cartService.deleteProduct(currentUser.getId(),productIds);
    }

    /**
     * 购物车列表
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "list.do")
    public ServerResponse<CartVo> list() {
        User currentUser = HttpContextUtils.getCurrentUser();
        return cartService.list(currentUser.getId());
    }


    /**
     * 选中所有
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "select_all.do")
    public ServerResponse<CartVo> selectAll() {
        User currentUser = HttpContextUtils.getCurrentUser();
        return cartService.selectOrUnSelectAll(currentUser.getId(),Const.Cart.CHECKED);
    }

    /**
     * 不选中所有
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "un_select_all.do")
    public ServerResponse<CartVo> unSelectAll() {
        User currentUser = HttpContextUtils.getCurrentUser();
        return cartService.selectOrUnSelectAll(currentUser.getId(),Const.Cart.UN_CHECKED);
    }


    /**
     * 选中单个
     * @param productId
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "select.do")
    public ServerResponse<CartVo> select(Integer productId) {
        User currentUser = HttpContextUtils.getCurrentUser();
        return cartService.selectOrUnSelect(currentUser.getId(), productId, Const.Cart.CHECKED);
    }

    /**
     * 不选中单个
     * @param productId
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "un_select.do")
    public ServerResponse<CartVo> unSelect(Integer productId) {
        User currentUser = HttpContextUtils.getCurrentUser();
        return cartService.selectOrUnSelect(currentUser.getId(), productId, Const.Cart.UN_CHECKED);
    }


    /**
     * 获得购物车总数量
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount() {
        User currentUser = HttpContextUtils.getCurrentUser();
        if (currentUser == null) {
            return ServerResponse.buildSuccess(0);
        }

        return cartService.getCartProductCount(currentUser.getId());
    }
}