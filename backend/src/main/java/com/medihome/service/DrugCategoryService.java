package com.medihome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medihome.dto.DrugCategoryDTO;
import com.medihome.entity.DrugCategory;

import java.util.List;

/**
 * 药品分类服务接口
 */
public interface DrugCategoryService extends IService<DrugCategory> {
    
    /**
     * 获取分类列表
     */
    List<DrugCategory> getList(Long userId);
    
    /**
     * 添加分类
     */
    void addCategory(Long userId, DrugCategoryDTO categoryDTO);
    
    /**
     * 修改分类
     */
    void updateCategory(Long userId, Long id, DrugCategoryDTO categoryDTO);
    
    /**
     * 删除分类
     */
    void deleteCategory(Long userId, Long id);
}
