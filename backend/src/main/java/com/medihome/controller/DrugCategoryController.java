package com.medihome.controller;

import com.medihome.dto.DrugCategoryDTO;
import com.medihome.entity.DrugCategory;
import com.medihome.service.DrugCategoryService;
import com.medihome.utils.UserContext;
import com.medihome.vo.ResultVO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 药品分类控制器
 */
@RestController
@RequestMapping("/api/drug-category")
public class DrugCategoryController {
    
    @Autowired
    private DrugCategoryService categoryService;
    
    /**
     * 获取分类列表
     */
    @GetMapping("/list")
    public ResultVO<List<DrugCategory>> list() {
        List<DrugCategory> list = categoryService.getList(UserContext.getUserId());
        return ResultVO.success(list);
    }
    
    /**
     * 添加分类
     */
    @PostMapping
    public ResultVO<Void> add(@Valid @RequestBody DrugCategoryDTO categoryDTO) {
        categoryService.addCategory(UserContext.getUserId(), categoryDTO);
        return ResultVO.success();
    }
    
    /**
     * 修改分类
     */
    @PutMapping("/{id}")
    public ResultVO<Void> update(@PathVariable Long id, @Valid @RequestBody DrugCategoryDTO categoryDTO) {
        categoryService.updateCategory(UserContext.getUserId(), id, categoryDTO);
        return ResultVO.success();
    }
    
    /**
     * 删除分类
     */
    @DeleteMapping("/{id}")
    public ResultVO<Void> delete(@PathVariable Long id) {
        categoryService.deleteCategory(UserContext.getUserId(), id);
        return ResultVO.success();
    }
}
