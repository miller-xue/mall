package com.mall.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by miller on 2018/10/4
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartVo {

    private List<CartProductVo> cartProductVoList;

    private BigDecimal cartTotalPrice;

    private Boolean allChecked;

    private String imageHost;
}
