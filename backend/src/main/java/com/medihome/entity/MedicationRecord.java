package com.medihome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用药记录实体类
 */
@Data
@TableName("medication_record")
public class MedicationRecord {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 记录创建者ID
     */
    private Long userId;
    
    /**
     * 用药成员ID
     */
    private Long memberId;
    
    /**
     * 药品ID
     */
    private Long drugId;
    
    /**
     * 用药剂量
     */
    private String dosage;
    
    /**
     * 用药时间
     */
    private LocalDateTime medicationTime;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    // 非数据库字段
    @TableField(exist = false)
    private String memberName;
    
    @TableField(exist = false)
    private String drugName;
    
    @TableField(exist = false)
    private String drugSpecification;
}
