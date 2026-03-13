package com.medihome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 库存变动记录实体类
 */
@Data
@TableName("drug_stock_log")
public class DrugStockLog {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 药品ID
     */
    private Long drugId;
    
    /**
     * 变动类型：1-增加，2-减少，3-盘点调整
     */
    private Integer changeType;
    
    /**
     * 变动数量
     */
    private Integer changeQuantity;
    
    /**
     * 变动前数量
     */
    private Integer beforeQuantity;
    
    /**
     * 变动后数量
     */
    private Integer afterQuantity;
    
    /**
     * 变动原因
     */
    private String reason;
    
    /**
     * 操作人ID
     */
    private Long operatorId;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    // 非数据库字段
    @TableField(exist = false)
    private String drugName;
    
    @TableField(exist = false)
    private String operatorName;
    
    @TableField(exist = false)
    private String changeTypeName;
}
