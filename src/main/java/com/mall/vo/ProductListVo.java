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
public class ProductListVo {

    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private BigDecimal price;

    private Integer status;

    private String imageHost;

}
