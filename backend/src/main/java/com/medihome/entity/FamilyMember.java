package com.medihome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 家庭成员实体类
 */
@Data
@TableName("family_member")
public class FamilyMember {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 创建者用户ID
     */
    private Long userId;
    
    /**
     * 成员姓名
     */
    private String name;
    
    /**
     * 年龄
     */
    private Integer age;
    
    /**
     * 性别：0-女，1-男
     */
    private Integer gender;
    
    /**
     * 过敏史
     */
    private String allergyHistory;
    
    /**
     * 常用药品
     */
    private String commonDrugs;
    
    /**
     * 是否默认成员：0-否，1-是
     */
    private Integer isDefault;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
