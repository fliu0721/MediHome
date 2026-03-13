package com.medihome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 系统配置实体类
 */
@Data
@TableName("sys_config")
public class SysConfig {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 用户ID（0为系统默认）
     */
    private Long userId;
    
    /**
     * 配置键
     */
    private String configKey;
    
    /**
     * 配置值
     */
    private String configValue;
    
    /**
     * 备注
     */
    private String remark;
}
