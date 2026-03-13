package com.medihome.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用药记录请求DTO
 */
@Data
public class MedicationRecordDTO {
    
    @NotNull(message = "用药成员不能为空")
    private Long memberId;
    
    @NotNull(message = "药品不能为空")
    private Long drugId;
    
    @NotBlank(message = "用药剂量不能为空")
    private String dosage;
    
    @NotNull(message = "用药时间不能为空")
    private LocalDateTime medicationTime;
    
    private String remark;
}
