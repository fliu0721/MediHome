package com.medihome.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalTime;

/**
 * 用药提醒请求DTO
 */
@Data
public class MedicationReminderDTO {
    
    @NotNull(message = "用药成员不能为空")
    private Long memberId;
    
    @NotNull(message = "药品不能为空")
    private Long drugId;
    
    @NotBlank(message = "用药剂量不能为空")
    private String dosage;
    
    @NotNull(message = "提醒时间不能为空")
    private LocalTime reminderTime;
    
    @NotNull(message = "频率类型不能为空")
    private Integer frequencyType;
    
    private String frequencyValue;
}
