package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.mall.common.ServerResponse;
import com.mall.dao.ShippingMapper;
import com.mall.pojo.Shipping;
import com.mall.service.IShippingService;
import com.mall.util.HttpContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by miller on 2018/10/6
 */
@Slf4j
@Service
public class ShippingServiceImpl implements IShippingService {

    @Autowired
    private ShippingMapper shippingMapper;



    public ServerResponse add(Shipping shipping) {
        shipping.setUserId(HttpContextUtils.getCurrentUser().getId());
        int rowCount = shippingMapper.insert(shipping);
        if (rowCount > 0) {
            Map result = Maps.newHashMap();
            result.put("shippingId", shipping.getId());
            return ServerResponse.buildSuccess("新建地址成功", result);
        }
        return ServerResponse.buildFail("新建地址失败");
    }


    public ServerResponse<String> del(Integer shippingId) {
        int resultCount = shippingMapper.deleteByPrimaryKeyUAndUserId(shippingId, HttpContextUtils.getCurrentUser().getId());
        if (resultCount > 0)
        {
            return ServerResponse.buildSuccessMsg("删除地址成功");
        }
        return ServerResponse.buildFail("删除地址失败");
    }

    @Override
    public ServerResponse update(Shipping shipping) {
        //防止纵向越权
        shipping.setUserId(HttpContextUtils.getCurrentUser().getId());

        int rowCount = shippingMapper.updateByPrimaryKeyAndUserId(shipping);
        if (rowCount > 0) {
            return ServerResponse.buildSuccessMsg("更新地址成功");
        }
        return ServerResponse.buildFail("更新地址失败");
    }

        @Override
        public ServerResponse<Shipping> select(Integer shippingId) {
            Shipping shipping = shippingMapper.selectByPrimaryKeyAndUserId(shippingId, HttpContextUtils.getCurrentUser().getId());
            if (shipping == null) {
                return ServerResponse.buildFail("无法查询到该地址");
            }
            return ServerResponse.buildSuccess("查询地址成功", shipping);
    }

    @Override
    public ServerResponse<PageInfo> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Shipping> shippings = shippingMapper.selectByUserId(HttpContextUtils.getCurrentUser().getId());

        PageInfo info = new PageInfo(shippings);
        return ServerResponse.buildSuccess(info);
    }
}
