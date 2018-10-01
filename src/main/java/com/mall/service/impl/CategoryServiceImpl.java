package com.mall.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mall.common.ServerResponse;
import com.mall.dao.CategoryMapper;
import com.mall.pojo.Category;
import com.mall.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by miller on 2018/10/1
 */
@Service
@Slf4j
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCategory(String categoryName, Integer parentId) {
        // TODO 参数校验不应该在这里做 jsr3003统一处理
        if (parentId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.buildFail("添加品类参数错误");
        }
        Category build = Category.builder().name(categoryName).parentId(parentId).status(true).build();
        int rowCount = categoryMapper.insertSelective(build);
        if (rowCount > 0) {
            return ServerResponse.buildSuccessMsg("添加品类成功");
        }else {
            return ServerResponse.buildFail("添加品类失败");
        }
    }

    public ServerResponse updateCategoryName(Integer categoryId, String categoryName) {
        if (categoryId == null || StringUtils.isBlank(categoryName)) {
            return ServerResponse.buildFail("添加品类参数错误");
        }
        Category category = Category.builder().id(categoryId).name(categoryName).build();
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServerResponse.buildSuccessMsg("更新品类名称成功");
        }else {
            return ServerResponse.buildFail("更新品类名称失败");
        }
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(int categoryId) {
        List<Category> categoryList = categoryMapper.selectChildrenByPId(categoryId);
        if (CollectionUtils.isEmpty(categoryList)) {
            log.info("未找到当前分类的子分类" + categoryId);
        }
        return ServerResponse.buildSuccess(categoryList);
    }

    public ServerResponse selectCategoryAndChildrenById(int categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);
        List<Integer> categoryIdList = Lists.newArrayList();
        for (Category item : categorySet) {
            categoryIdList.add(item.getId());
        }
        return ServerResponse.buildSuccess(categoryIdList);
    }


    // 递归算法算出子节点 TODO
    private Set<Category> findChildCategory(Set<Category> categorySet, int categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        // 查找子节点,递归算法要有一个退出的条件
        List<Category> categoryList = categoryMapper.selectChildrenByPId(categoryId);
        for (Category c : categoryList) {
            findChildCategory(categorySet, c.getId());
        }
        return categorySet;
    }
}
