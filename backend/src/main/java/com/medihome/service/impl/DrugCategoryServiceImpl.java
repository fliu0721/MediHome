package com.medihome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medihome.dto.DrugCategoryDTO;
import com.medihome.entity.DrugCategory;
import com.medihome.mapper.DrugCategoryMapper;
import com.medihome.service.DrugCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 药品分类服务实现类
 */
@Service
public class DrugCategoryServiceImpl extends ServiceImpl<DrugCategoryMapper, DrugCategory> 
    implements DrugCategoryService {
    
    @Override
    public List<DrugCategory> getList(Long userId) {
        return baseMapper.selectByUserId(userId);
    }
    
    @Override
    public void addCategory(Long userId, DrugCategoryDTO categoryDTO) {
        DrugCategory category = new DrugCategory();
        BeanUtils.copyProperties(categoryDTO, category);
        category.setUserId(userId);
        category.setCreateTime(LocalDateTime.now());
        baseMapper.insert(category);
    }
    
    @Override
    public void updateCategory(Long userId, Long id, DrugCategoryDTO categoryDTO) {
        DrugCategory category = baseMapper.selectById(id);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }
        // 只能修改自己创建的分类
        if (!category.getUserId().equals(userId)) {
            throw new RuntimeException("无权限修改系统默认分类");
        }
        
        BeanUtils.copyProperties(categoryDTO, category);
        category.setId(id);
        baseMapper.updateById(category);
    }
    
    @Override
    public void deleteCategory(Long userId, Long id) {
        DrugCategory category = baseMapper.selectById(id);
        if (category == null) {
            throw new RuntimeException("分类不存在");
        }
        // 只能删除自己创建的分类
        if (!category.getUserId().equals(userId)) {
            throw new RuntimeException("无权限删除系统默认分类");
        }
        baseMapper.deleteById(id);
    }
}
