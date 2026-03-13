package com.medihome.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 修改密码请求DTO
 */
@Data
public class UpdatePasswordDTO {
    
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;
    
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}
