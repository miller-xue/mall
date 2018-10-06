package com.mall.service;

import com.github.pagehelper.PageInfo;
import com.mall.common.ServerResponse;
import com.mall.pojo.Shipping;

/**
 * Created by miller on 2018/10/6
 */
public interface IShippingService {

    /**
     * 增
     * @param shipping
     * @return
     */
    ServerResponse add(Shipping shipping);

    /**
     * 删
     * @param shippingId
     * @return
     */
    ServerResponse<String> del(Integer shippingId);

    /**
     * 改
     * @param shipping
     * @return
     */
    ServerResponse update(Shipping shipping);

    /**
     * 查
     * @param shippingId
     * @return
     */
    ServerResponse<Shipping> select(Integer shippingId);

    /**
     * 列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> list(int pageNum, int pageSize);
}
