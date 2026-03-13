package com.medihome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medihome.entity.FamilyMember;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 家庭成员Mapper
 */
@Mapper
public interface FamilyMemberMapper extends BaseMapper<FamilyMember> {
    
    @Select("SELECT * FROM family_member WHERE user_id = #{userId} ORDER BY is_default DESC, create_time ASC")
    List<FamilyMember> selectByUserId(Long userId);
    
    @Update("UPDATE family_member SET is_default = 0 WHERE user_id = #{userId}")
    void clearDefaultByUserId(Long userId);
}
