package com.medihome.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * 药品请求DTO
 */
@Data
public class DrugDTO {
    
    private Long categoryId;
    
    @NotBlank(message = "药品名称不能为空")
    private String name;
    
    private String specification;
    
    private String manufacturer;
    
    private LocalDate produceDate;
    
    @NotNull(message = "有效期不能为空")
    private LocalDate expiryDate;
    
    private String usageDosage;
    
    private String indication;
    
    private String contraindication;
    
    private String storageCondition;
    
    private LocalDate purchaseDate;
    
    @NotNull(message = "库存数量不能为空")
    private Integer stockQuantity;
    
    private String stockUnit;
    
    private String location;
    
    private String images;
    
    private String remark;
}
