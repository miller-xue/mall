package com.mall.controller.backend;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
import com.mall.common.annotation.Admin;
import com.mall.pojo.User;
import com.mall.service.ICategoryService;
import com.mall.service.IUserService;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



/**
 * Created by miller on 2018/10/1
 */
@Controller
@RequestMapping(value = "/manage/category")
public class CategoryManageController {


    @Autowired
    private ICategoryService categoryService;

    @Admin
    @ResponseBody
    @RequestMapping(value = "/add_category.do", method = RequestMethod.POST)
    public ServerResponse add(String categoryName,
                              @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        return categoryService.addCategory(categoryName, parentId);
    }

    @Admin
    @ResponseBody
    @RequestMapping(value = "/set_category_name.do",method = RequestMethod.POST)
    public ServerResponse setCategoryName(Integer categoryId,
                                          String categoryName) {
        return categoryService.updateCategoryName(categoryId, categoryName);
    }

    @Admin
    @ResponseBody
    @RequestMapping(value = "/get_children_parallel_category.do",method = RequestMethod.POST)
    public ServerResponse getChildrenParallelCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return categoryService.getChildrenParallelCategory(categoryId);
    }

    @Admin
    @ResponseBody
    @RequestMapping(value = "/get_deep_category.do",method = RequestMethod.POST)
    public ServerResponse getCategoryAndDeepChildrenCategory(@RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        return categoryService.selectCategoryAndChildrenById(categoryId);
    }
}
