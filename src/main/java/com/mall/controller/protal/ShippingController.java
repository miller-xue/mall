package com.mall.controller.protal;

import com.github.pagehelper.PageInfo;
import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.common.annotation.NeedLogin;
import com.mall.pojo.Shipping;
import com.mall.service.IShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by miller on 2018/10/6
 */
@Controller
@RequestMapping(value = "/shipping/")
public class ShippingController {


    @Autowired
    private IShippingService shippingService;


    /**
     * 新增一个收货地址
     * @param shipping 对象
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "add.do")
    public ServerResponse add(Shipping shipping) {
        return shippingService.add(shipping);
    }

    /**
     * 删除一个收货地址
     * @param shippingId id
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "del.do")
    public ServerResponse del(Integer shippingId) {
        return shippingService.del(shippingId);
    }

    /**
     * 修改一个收货地址
     * @param shipping 对象
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "update.do")
    public ServerResponse update(Shipping shipping) {
        return shippingService.update(shipping);
    }

    /**
     * 查询一个收货地址
     * @param shippingId 对象
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "select.do")
    public ServerResponse select(Integer shippingId) {
        return shippingService.select(shippingId);
    }

    /**
     * 收货地址列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @NeedLogin
    @ResponseBody
    @RequestMapping(value = "list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = Const.Page.pageNum) int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = Const.Page.pageSize) int pageSize) {
        return shippingService.list(pageNum, pageSize);
    }

}
