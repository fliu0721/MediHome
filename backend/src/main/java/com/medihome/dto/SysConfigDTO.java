package com.medihome.dto;

import lombok.Data;

/**
 * 系统配置请求DTO
 */
@Data
public class SysConfigDTO {
    
    private Integer expiryReminderDays;
    
    private Integer stockWarningThreshold;
    
    private Boolean enableNotification;
}
