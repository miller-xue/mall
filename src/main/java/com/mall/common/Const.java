package com.mall.common;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

/**
 * Created by miller on 2018/9/24
 */
public interface Const {
    String CURRENT_USER = "CURRENT_USER";

    String EMAIL = "email";

    String USERNAME = "username";

    /**
     * 角色相关常量
     */
    interface Role{
        /**
         * 普通用户
         */
        int ROLE_CUSTOMER = 0;

        /**
         * 管理员
         */
        int ROLE_ADMIN = 1;
    }

    interface Page {
        String pageNum = "1";

        String pageSize = "10";
    }

    @Getter
    @AllArgsConstructor
    enum ProductStatusEnum {
        ON_SALE("在线", 1),

        ;
        private String value;

        private int code;
    }

    interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }
}
