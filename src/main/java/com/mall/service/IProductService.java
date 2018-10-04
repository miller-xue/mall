package com.mall.service;

import com.github.pagehelper.PageInfo;
import com.mall.common.ServerResponse;
import com.mall.pojo.Product;
import com.mall.vo.ProductDetailVo;

/**
 * Created by miller on 2018/10/3
 */
public interface IProductService {

    ServerResponse saveOrUpdate(Product product);

    ServerResponse<String> setSaleStatus(Integer productId, Integer status);

    ServerResponse<ProductDetailVo> managProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(int pageNum, int pageSize);

    ServerResponse<PageInfo> searchProductList(int pageNum, int pageSize,
                                               String productName, Integer productId);

    ServerResponse<ProductDetailVo> getProductDetail(Integer id);


    ServerResponse<PageInfo> getProductByKeywordCategory(int pageNum, int pageSize,
                                                         String keyword, Integer categoryId,
                                                         String orderBy);
}
