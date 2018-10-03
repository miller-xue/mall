package com.mall.common;

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

}
