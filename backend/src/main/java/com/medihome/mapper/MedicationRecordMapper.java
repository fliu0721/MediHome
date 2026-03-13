package com.medihome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.medihome.entity.MedicationRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 用药记录Mapper
 */
@Mapper
public interface MedicationRecordMapper extends BaseMapper<MedicationRecord> {
    
    Page<MedicationRecord> selectRecordPage(Page<MedicationRecord> page,
                                            @Param("userId") Long userId,
                                            @Param("memberId") Long memberId,
                                            @Param("drugId") Long drugId,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);
    
    @Select("SELECT COUNT(*) FROM medication_record WHERE user_id = #{userId} " +
            "AND medication_time >= #{startDate} AND medication_time <= #{endDate}")
    Long selectCountByDateRange(@Param("userId") Long userId,
                                 @Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate);
    
    @Select("SELECT m.member_id as memberId, m.name as memberName, COUNT(*) as count " +
            "FROM medication_record r JOIN family_member m ON r.member_id = m.id " +
            "WHERE r.user_id = #{userId} AND r.medication_time >= #{startDate} AND r.medication_time <= #{endDate} " +
            "GROUP BY m.member_id, m.name")
    List<Map<String, Object>> selectMemberStats(@Param("userId") Long userId,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
    
    @Select("SELECT r.drug_id as drugId, d.name as drugName, COUNT(*) as count " +
            "FROM medication_record r JOIN drug d ON r.drug_id = d.id " +
            "WHERE r.user_id = #{userId} AND r.medication_time >= #{startDate} AND r.medication_time <= #{endDate} " +
            "GROUP BY r.drug_id, d.name ORDER BY count DESC LIMIT 10")
    List<Map<String, Object>> selectDrugStats(@Param("userId") Long userId,
                                               @Param("startDate") LocalDateTime startDate,
                                               @Param("endDate") LocalDateTime endDate);
}
