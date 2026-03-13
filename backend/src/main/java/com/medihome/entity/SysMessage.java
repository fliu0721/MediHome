package com.medihome.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 系统消息实体类
 */
@Data
@TableName("sys_message")
public class SysMessage {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /**
     * 接收用户ID
     */
    private Long userId;
    
    /**
     * 消息类型：1-过期提醒，2-库存预警，3-系统通知
     */
    private Integer type;
    
    /**
     * 消息标题
     */
    private String title;
    
    /**
     * 消息内容
     */
    private String content;
    
    /**
     * 关联业务ID
     */
    private Long relatedId;
    
    /**
     * 是否已读：0-否，1-是
     */
    private Integer isRead;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    // 非数据库字段
    @TableField(exist = false)
    private String typeName;
}
