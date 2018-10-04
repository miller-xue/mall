package com.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.dao.ProductMapper;
import com.mall.pojo.Category;
import com.mall.pojo.Product;
import com.mall.service.ICategoryService;
import com.mall.service.IProductService;
import com.mall.util.DateTimeUtil;
import com.mall.util.PropertiesUtil;
import com.mall.vo.ProductDetailVo;
import com.mall.vo.ProductListVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by miller on 2018/10/3
 */
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ICategoryService categoryService;

    public ServerResponse saveOrUpdate(Product product) {
        if (product != null)
        {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImgArray = product.getSubImages().split(",");
                product.setMainImage(subImgArray.length > 0 ? subImgArray[0] : "");
            }

            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKeySelective(product);
                if (rowCount > 0) {
                    return ServerResponse.buildSuccessMsg("更新产品成功");
                }
                return ServerResponse.buildFail("更新产品失败");
            }else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServerResponse.buildSuccessMsg("新增产品成功");
                }
                return ServerResponse.buildFail("新增产品失败");
            }
        }
        return ServerResponse.buildFail("新增或更新产品参数不正确");
    }

    public ServerResponse<String> setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.buildFail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = Product.builder().id(productId).status(status).build();
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.buildSuccessMsg("修改上下架状态成功");
        }
        return ServerResponse.buildFail("修改上下架状态失败");
    }

    public ServerResponse<ProductDetailVo> managProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.buildFail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.buildFail("产品已下架或者删除");
        }
        //VO 对象 value object
        return ServerResponse.buildSuccess(assembleProductDetailVo(product));
    }


    private ProductDetailVo assembleProductDetailVo(Product product) {
        ServerResponse<Category> categoryServerResponse = categoryService.findById(product.getCategoryId());
        return ProductDetailVo.builder()
                .id(product.getId())
                .subtitle(product.getSubtitle())
                .price(product.getPrice())
                .mainImage(product.getMainImage())
                .subImages(product.getSubImages())
                .categoryId(product.getCategoryId())
                .detail(product.getDetail())
                .name(product.getName())
                .status(product.getStatus())
                .stock(product.getStock())
                .imageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"))
                .parentCategoryId(categoryServerResponse.isSuccess() ? categoryServerResponse.getData().getId() : 0)
                .createTime(DateTimeUtil.dateToStr(product.getCreateTime()))
                .updateTime(DateTimeUtil.dateToStr(product.getUpdateTime()))
                .build();
    }


    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        // startpage -start
        //填充自己查询逻辑
        // 1.开始分页
        PageHelper.startPage(pageNum, pageSize);

        // 2.数据查询
        List<Product> productList = productMapper.selectList();

        List<ProductListVo> productListVoList = new ArrayList<>(productList.size());
        for (Product product : productList) {
            productListVoList.add(assembleProductListVo(product));
        }
        // 3.结束分页
        PageInfo pageResult = new PageInfo(productList);

        pageResult.setList(productListVoList);

        return ServerResponse.buildSuccess(pageResult);
    }

    private ProductListVo assembleProductListVo(Product product) {
        ProductListVo listVo = new ProductListVo();
        BeanUtils.copyProperties(product, listVo);
        listVo.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        return listVo;
    }



    public ServerResponse<PageInfo> searchProductList(int pageNum, int pageSize,
                                                      String productName, Integer productId) {
        // 1.开始分页
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder(productName.length() + 2).
                    append("%").append(productName.trim()).append("%").toString();
        }

        // 2.数据查询
        List<Product> productList = productMapper.selectByNameAndId(StringUtils.isBlank(productName) ? null : productName, productId);

        List<ProductListVo> productListVoList = new ArrayList<>(productList.size());
        for (Product product : productList) {
            productListVoList.add(assembleProductListVo(product));
        }
        // 3.结束分页
        PageInfo pageResult = new PageInfo(productList);

        pageResult.setList(productListVoList);

        return ServerResponse.buildSuccess(pageResult);
    }


    public ServerResponse<ProductDetailVo> getProductDetail(Integer id) {
        if (id == null) {
            return ServerResponse.buildFail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = productMapper.selectByPrimaryKey(id);
        if (product == null) {
            return ServerResponse.buildFail("产品已删除");
        }
        if (product.getStatus().intValue() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.buildFail("该产品已下架");
        }

        //VO 对象 value object
        return ServerResponse.buildSuccess(assembleProductDetailVo(product));
    }

    //TODO
    public ServerResponse<PageInfo> getProductByKeywordCategory(int pageNum, int pageSize,
                                                                String keyword, Integer categoryId,
                                                                String orderBy) {
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.buildFail(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        List<Integer> categoryIdList = new ArrayList<Integer>();
        if (categoryId != null) {
            if (!categoryService.findById(categoryId).isSuccess()) {
                // 没有该分类,并且没有关键字,这个时候返回一个i空的结果集,不报错
                PageHelper.startPage(pageNum, pageSize);
                return ServerResponse.buildSuccess(new PageInfo(Lists.newArrayList()));
            }
            categoryIdList = categoryService.selectCategoryAndChildrenById(categoryId).getData();
        }
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder(keyword.length() + 2).
                    append("%").append(keyword.trim()).append("%").toString();
        }
        // 开始正式查询
        PageHelper.startPage(pageNum, pageSize);
        // 排序处理
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] s = orderBy.split("_");
                PageHelper.orderBy(s[0] + " " + s[1]);
            }
        }
        List<Product> productList = productMapper.selectByKeywordCategoryIds(StringUtils.isBlank(keyword) ? null : keyword,
                categoryIdList.size() == 0 ? null : categoryIdList);

        List<ProductListVo> productListVoList = new ArrayList<>(productList.size());
        for (Product product : productList) {
            productListVoList.add(assembleProductListVo(product));
        }
        // 3.结束分页
        PageInfo pageResult = new PageInfo(productList);

        pageResult.setList(productListVoList);

        return ServerResponse.buildSuccess(pageResult);
    }
}
