package com.medihome.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 库存变动请求DTO
 */
@Data
public class DrugStockChangeDTO {
    
    @NotNull(message = "变动类型不能为空")
    private Integer changeType;
    
    @NotNull(message = "变动数量不能为空")
    private Integer changeQuantity;
    
    private String reason;
}
