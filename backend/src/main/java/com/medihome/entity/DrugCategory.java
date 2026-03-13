package com.medihome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 药品分类实体类
 */
@Data
@TableName("drug_category")
public class DrugCategory {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 创建者ID（0为系统默认）
     */
    private Long userId;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 排序号
     */
    private Integer sortOrder;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
