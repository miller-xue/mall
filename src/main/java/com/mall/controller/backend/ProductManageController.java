package com.mall.controller.backend;

import com.mall.common.ServerResponse;
import com.mall.common.annotation.Admin;
import com.mall.pojo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by miller on 2018/10/3
 */

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Admin
    @ResponseBody
    @RequestMapping("/save.do")
    public ServerResponse save(HttpSession session, Product product) {
        System.out.println("");
        return null;
    }

}
