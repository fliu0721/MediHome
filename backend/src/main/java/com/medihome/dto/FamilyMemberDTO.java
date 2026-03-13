package com.medihome.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 家庭成员请求DTO
 */
@Data
public class FamilyMemberDTO {
    
    @NotBlank(message = "成员姓名不能为空")
    private String name;
    
    private Integer age;
    
    private Integer gender;
    
    private String allergyHistory;
    
    private String commonDrugs;
    
    private Integer isDefault;
}
