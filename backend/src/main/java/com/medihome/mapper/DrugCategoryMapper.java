package com.medihome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medihome.entity.DrugCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 药品分类Mapper
 */
@Mapper
public interface DrugCategoryMapper extends BaseMapper<DrugCategory> {
    
    @Select("SELECT * FROM drug_category WHERE user_id = 0 OR user_id = #{userId} ORDER BY sort_order ASC, create_time ASC")
    List<DrugCategory> selectByUserId(Long userId);
}
