package com.medihome.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 药品分类请求DTO
 */
@Data
public class DrugCategoryDTO {
    
    @NotBlank(message = "分类名称不能为空")
    private String name;
    
    private Integer sortOrder;
}
