package com.medihome.dto;

import lombok.Data;

import java.time.LocalDate;

/**
 * 用药记录查询请求DTO
 */
@Data
public class MedicationRecordQueryDTO {
    
    private Integer pageNum = 1;
    
    private Integer pageSize = 10;
    
    private Long memberId;
    
    private Long drugId;
    
    private LocalDate startDate;
    
    private LocalDate endDate;
}
