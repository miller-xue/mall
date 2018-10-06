package com.mall.service;

import com.github.pagehelper.PageInfo;
import com.mall.common.ServerResponse;
import com.mall.pojo.Shipping;

/**
 * Created by miller on 2018/10/6
 */
public interface IShippingService {

    ServerResponse add(Shipping shipping);


    ServerResponse<String> del(Integer shippingId);

    ServerResponse update(Shipping shipping);

    ServerResponse<Shipping> select(Integer shippingId);

    ServerResponse<PageInfo> list(int pageNum, int pageSize);
}
