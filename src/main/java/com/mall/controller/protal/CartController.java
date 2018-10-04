package com.mall.controller.protal;

import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.common.annotation.NeedLogin;
import com.mall.pojo.User;
import com.mall.service.ICartService;
import com.mall.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by miller on 2018/10/4
 */
@Controller
@RequestMapping(value = "/cart/")
public class CartController {

    @Autowired
    private ICartService cartService;


    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "add.do")
    public ServerResponse<CartVo> add(HttpServletRequest request,
                                      Integer productId, Integer count) {
        //TODO 用户从 线程中取出
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        return cartService.add(currentUser.getId(), productId, count);
    }

    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "update.do")
    public ServerResponse<CartVo> update(HttpServletRequest request,
                                      Integer productId, Integer count) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        return cartService.update(currentUser.getId(), productId, count);
    }

    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "delete_product.do")
    public ServerResponse<CartVo> deleteProduct(HttpServletRequest request,
                                      String productIds) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        return cartService.deleteProduct(currentUser.getId(),productIds);
    }

    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "list.do")
    public ServerResponse<CartVo> list(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        return cartService.list(currentUser.getId());
    }


    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "select_all.do")
    public ServerResponse<CartVo> selectAll(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        return cartService.selectOrUnSelectAll(currentUser.getId(),Const.Cart.CHECKED);
    }

    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "un_select_all.do")
    public ServerResponse<CartVo> unSelectAll(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        return cartService.selectOrUnSelectAll(currentUser.getId(),Const.Cart.UN_CHECKED);
    }


    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "select.do")
    public ServerResponse<CartVo> select(HttpServletRequest request, Integer productId) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        return cartService.selectOrUnSelect(currentUser.getId(), productId, Const.Cart.CHECKED);
    }

    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "un_select.do")
    public ServerResponse<CartVo> unSelect(HttpServletRequest request, Integer productId) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        return cartService.selectOrUnSelect(currentUser.getId(), productId, Const.Cart.UN_CHECKED);
    }



    @ResponseBody
    @RequestMapping(value = "get_cart_product_count.do")
    public ServerResponse<Integer> getCartProductCount(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.buildSuccess(0);
        }

        return cartService.getCartProductCount(currentUser.getId());
    }






    // 全选

    // 全反选

    // 单独选

    // 单独反选


    // 查询当前用户购物车的产品数量



}
