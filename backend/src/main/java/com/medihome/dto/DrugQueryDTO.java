package com.medihome.dto;

import lombok.Data;

/**
 * 药品查询请求DTO
 */
@Data
public class DrugQueryDTO {
    
    private Integer pageNum = 1;
    
    private Integer pageSize = 10;
    
    private String keyword;
    
    private Long categoryId;
    
    private Integer status;
    
    private String location;
    
    private Integer isFavorite;
}
