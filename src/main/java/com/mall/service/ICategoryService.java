package com.mall.service;

import com.mall.common.ServerResponse;
import com.mall.pojo.Category;

import java.util.List;

/**
 * Created by miller on 2018/10/1
 */
public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(Integer categoryId, String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(int categoryId);


    /**
     * 递归查询本节点的id和子节点的id
     * @param categoryId 本节点id
     * @return
     */
    ServerResponse selectCategoryAndChildrenById(int categoryId);

    ServerResponse<Category> findById(Integer id);

}
