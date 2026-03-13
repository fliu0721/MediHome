package com.medihome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * 用药提醒实体类
 */
@Data
@TableName("medication_reminder")
public class MedicationReminder {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 创建者ID
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
     * 提醒时间
     */
    private LocalTime reminderTime;
    
    /**
     * 频率类型：1-每日，2-每周，3-自定义
     */
    private Integer frequencyType;
    
    /**
     * 频率值
     */
    private String frequencyValue;
    
    /**
     * 状态：0-暂停，1-正常
     */
    private Integer status;
    
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
    private String frequencyTypeName;
}
