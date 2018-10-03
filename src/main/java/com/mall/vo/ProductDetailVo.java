package com.mall.vo;

import lombok.*;

import java.math.BigDecimal;

/**
 * Created by miller on 2018/10/3
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDetailVo {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private String subImages;

    private String detail;

    private BigDecimal price;

    private Integer stock;

    private Integer status;

    private String createTime;

    private String updateTime;

    private String imageHost;

    private Integer parentCategoryId;
}
