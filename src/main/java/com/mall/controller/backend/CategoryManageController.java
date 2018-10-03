package com.mall.controller.backend;

import com.mall.common.Const;
import com.mall.common.ResponseCode;
import com.mall.common.ServerResponse;
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
    private IUserService userService;


    @Autowired
    private ICategoryService categoryService;


    @ResponseBody
    @RequestMapping(value = "/add_category.do", method = RequestMethod.POST)
    public ServerResponse add(HttpSession session, String categoryName,
                              @RequestParam(value = "parentId", defaultValue = "0") int parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildFail(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆");
        }
        if (userService.isAdmin(user).isSuccess()) {
            return categoryService.addCategory(categoryName, parentId);
        }else {
            return ServerResponse.buildFail("无权限操作,需要管理员权限");
        }
    }


    @ResponseBody
    @RequestMapping(value = "/set_category_name.do",method = RequestMethod.POST)
    public ServerResponse setCategoryName(HttpSession session,
                                          Integer categoryId,
                                          String categoryName) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildFail(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆");
        }
        if (userService.isAdmin(user).isSuccess()) {
            // 更新categoryName
            return categoryService.updateCategoryName(categoryId, categoryName);
        } else {
            return ServerResponse.buildFail("无权限操作,需要管理员权限");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get_children_parallel_category.do",method = RequestMethod.POST)
    public ServerResponse getChildrenParallelCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildFail(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆");
        }
        if (userService.isAdmin(user).isSuccess()) {

            return categoryService.getChildrenParallelCategory(categoryId);
        } else {
            return ServerResponse.buildFail("无权限操作,需要管理员权限");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/get_deep_category.do",method = RequestMethod.POST)
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,
                                                      @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.buildFail(ResponseCode.NEED_LOGIN.getCode(), "用户未登陆,请登陆");
        }
        if (userService.isAdmin(user).isSuccess()) {
            // 查询当前节点的id 和递归子节点的id
            return categoryService.selectCategoryAndChildrenById(categoryId);
        } else {
            return ServerResponse.buildFail("无权限操作,需要管理员权限");
        }
    }
}
