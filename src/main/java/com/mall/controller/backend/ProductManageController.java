package com.mall.controller.backend;

import com.google.common.collect.Maps;
import com.mall.common.Const;
import com.mall.common.ServerResponse;
import com.mall.common.SysConfig;
import com.mall.common.annotation.Admin;
import com.mall.pojo.Product;
import com.mall.pojo.User;
import com.mall.service.IFileService;
import com.mall.service.IProductService;
import com.mall.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by miller on 2018/10/3
 */

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IProductService productService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IFileService fileService;


    /**
     * 保存一个商品
     * @param product
     * @return
     */
    @Admin
    @ResponseBody
    @RequestMapping("/save.do")
    public ServerResponse save(Product product) {
        return productService.saveOrUpdate(product);
    }

    /**
     * 设置商品上下架状态
     * @param productId
     * @param status
     * @return
     */
    @Admin
    @ResponseBody
    @RequestMapping("/set_sale_status.do")
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        return productService.setSaleStatus(productId, status);
    }


    /**
     * 商品详情
     * @param productId
     * @return
     */
    @Admin
    @ResponseBody
    @RequestMapping("/detail.do")
    public ServerResponse getDetail(Integer productId) {
        return productService.managProductDetail(productId);
    }

    /**
     * 商品列表
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Admin
    @ResponseBody
    @RequestMapping("/list.do")
    public ServerResponse list(@RequestParam(value = "pageNum", defaultValue = Const.Page.pageNum) int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = Const.Page.pageSize) int pageSize) {
        return productService.getProductList(pageNum, pageSize);
    }

    /**
     * 查询一个商品
     * @param pageNum
     * @param pageSize
     * @param productName
     * @param productId
     * @return
     */
    @Admin
    @ResponseBody
    @RequestMapping("/search.do")
    public ServerResponse search(@RequestParam(value = "pageNum", defaultValue = Const.Page.pageNum) int pageNum,
                                 @RequestParam(value = "pageSize", defaultValue = Const.Page.pageSize) int pageSize,
                                 String productName, Integer productId) {
        return productService.searchProductList(pageNum, pageSize, productName, productId);
    }

    /**
     * 上传图片
     * @param file
     * @param request
     * @return
     */
    @Admin
    @ResponseBody
    @RequestMapping("/upload.do")
    public ServerResponse upload(@RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file, path);
        if (StringUtils.isBlank(targetFileName)) {
            return ServerResponse.buildFail("上传失败");
        }

        String url = SysConfig.ftpServerHttpPrefix + targetFileName;
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFileName);
        fileMap.put("url", url);
        return ServerResponse.buildSuccess(fileMap);
    }

    /**
     * 富文本上传图片
     * @param session
     * @param response
     * @param file
     * @param request
     * @return
     */
    @Admin
    @ResponseBody
    @RequestMapping("/richtext_img_upload.do")
    public Map richtextImgUpload(HttpSession session, HttpServletResponse response,
                                 @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        Map resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员");
            return resultMap;
        }
        if (!userService.isAdmin(user).isSuccess()) {
            resultMap.put("success", false);
            resultMap.put("msg", "无权限操作");
            return resultMap;
        }


        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileName = fileService.upload(file, path);
        if (StringUtils.isBlank(targetFileName)) {
            resultMap.put("success", false);
            resultMap.put("msg", "上传失败");
            return resultMap;
        }
        String url = SysConfig.ftpServerHttpPrefix + targetFileName;
        resultMap.put("success", true);
        resultMap.put("msg", "上传成功");
        resultMap.put("file_path", url);
        response.addHeader("Access-Control-Allow-Headers","X-File-Name");
        return resultMap;
    }

}
