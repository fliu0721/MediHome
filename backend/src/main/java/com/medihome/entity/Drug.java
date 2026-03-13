package com.medihome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 药品实体类
 */
@Data
@TableName("drug")
public class Drug {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 创建者ID
     */
    private Long userId;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 药品名称
     */
    private String name;
    
    /**
     * 规格
     */
    private String specification;
    
    /**
     * 生产厂家
     */
    private String manufacturer;
    
    /**
     * 生产日期
     */
    private LocalDate produceDate;
    
    /**
     * 有效期
     */
    private LocalDate expiryDate;
    
    /**
     * 用法用量
     */
    private String usageDosage;
    
    /**
     * 适应症
     */
    private String indication;
    
    /**
     * 禁忌
     */
    private String contraindication;
    
    /**
     * 储存条件
     */
    private String storageCondition;
    
    /**
     * 采购日期
     */
    private LocalDate purchaseDate;
    
    /**
     * 库存数量
     */
    private Integer stockQuantity;
    
    /**
     * 库存单位
     */
    private String stockUnit;
    
    /**
     * 存放位置
     */
    private String location;
    
    /**
     * 图片URL（JSON数组）
     */
    private String images;
    
    /**
     * 状态：0-正常，1-即将过期，2-已过期，3-已归档
     */
    private Integer status;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 是否收藏：0-否，1-是
     */
    private Integer isFavorite;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    // 非数据库字段
    @TableField(exist = false)
    private String categoryName;
}
