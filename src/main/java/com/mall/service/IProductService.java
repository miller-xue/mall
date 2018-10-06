package com.mall.service;

import com.github.pagehelper.PageInfo;
import com.mall.common.ServerResponse;
import com.mall.pojo.Product;
import com.mall.vo.ProductDetailVo;

/**
 * Created by miller on 2018/10/3
 */
public interface IProductService {

    /**
     * 更新或修改一个商品
     * @param product
     * @return
     */
    ServerResponse saveOrUpdate(Product product);

    /**
     * 设置商品销售状态
     * @param productId
     * @param status
     * @return
     */
    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    /**
     * 后台商品详情
     * @param productId
     * @return
     */
    ServerResponse<ProductDetailVo> managProductDetail(Integer productId);

    /**
     * 商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    /**
     * 搜索商品列表
     * @param pageNum
     * @param pageSize
     * @param productName
     * @param productId
     * @return
     */
    ServerResponse<PageInfo> searchProductList(int pageNum, int pageSize,
                                               String productName, Integer productId);

    /**
     * 前台商品详情
     * @param id
     * @return
     */
    ServerResponse<ProductDetailVo> getProductDetail(Integer id);


    ServerResponse<PageInfo> getProductByKeywordCategory(int pageNum, int pageSize,
                                                         String keyword, Integer categoryId,
                                                         String orderBy);
}
