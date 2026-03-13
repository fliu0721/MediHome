package com.medihome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medihome.entity.Drug;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDate;
import java.util.List;

/**
 * 药品Mapper
 */
@Mapper
public interface DrugMapper extends BaseMapper<Drug> {
    
    Page<Drug> selectDrugPage(Page<Drug> page, 
                              @Param("userId") Long userId,
                              @Param("keyword") String keyword,
                              @Param("categoryId") Long categoryId,
                              @Param("status") Integer status,
                              @Param("location") String location,
                              @Param("isFavorite") Integer isFavorite);
    
    @Select("SELECT d.*, c.name as category_name FROM drug d LEFT JOIN drug_category c ON d.category_id = c.id WHERE d.id = #{id}")
    Drug selectDrugDetail(Long id);
    
    @Select("SELECT * FROM drug WHERE user_id = #{userId} AND status = 1 AND expiry_date <= #{date}")
    List<Drug> selectExpiringDrugs(@Param("userId") Long userId, @Param("date") LocalDate date);
    
    @Select("SELECT * FROM drug WHERE user_id = #{userId} AND status = 2")
    List<Drug> selectExpiredDrugs(Long userId);
    
    @Update("UPDATE drug SET status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
