package com.mall.controller.protal;

import com.github.pagehelper.PageInfo;
import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.service.IProductService;
import com.mall.vo.ProductDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by miller on 2018/10/4
 */
@Controller
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private IProductService productService;


    @ResponseBody
    @RequestMapping(value = "/detail.do")
    public ServerResponse<ProductDetailVo> detail(Integer productId) {
        return productService.getProductDetail(productId);
    }
    @ResponseBody
    @RequestMapping(value = "/list.do")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum", defaultValue = Const.Page.pageNum) int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = Const.Page.pageSize) int pageSize,
                                         @RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {
        return productService.getProductByKeywordCategory(pageNum, pageSize, keyword, categoryId, orderBy);
    }
}
