package com.mall.vo;

import lombok.*;

import java.math.BigDecimal;

/**
 * Created by miller on 2018/10/4
 * 结合产品和购物车的抽象对象
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartProductVo {

    private Integer id;

    private Integer userId;

    private Integer productId;

    /**
     * 购物车中商品数量
     */
    private Integer quantity;


    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    private BigDecimal productTotalPrice;

    private Integer productStock;

    /**
     * 商品是否被勾选
     */
    private Integer productChecked;

    /**
     * 限制数量的一个返回结果
     */
    private String limitQuantity;



}
