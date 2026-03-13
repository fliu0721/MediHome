package com.medihome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.medihome.entity.MedicationReminder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用药提醒Mapper
 */
@Mapper
public interface MedicationReminderMapper extends BaseMapper<MedicationReminder> {
    
    @Select("SELECT r.*, m.name as member_name, d.name as drug_name " +
            "FROM medication_reminder r " +
            "LEFT JOIN family_member m ON r.member_id = m.id " +
            "LEFT JOIN drug d ON r.drug_id = d.id " +
            "WHERE r.user_id = #{userId} ORDER BY r.reminder_time ASC")
    List<MedicationReminder> selectByUserId(Long userId);
    
    @Update("UPDATE medication_reminder SET status = #{status} WHERE id = #{id}")
    void updateStatus(Long id, Integer status);
}
