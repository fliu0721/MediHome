package com.medihome.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 注册请求DTO
 */
@Data
public class RegisterDTO {
    
    @NotBlank(message = "手机号不能为空")
    private String phone;
    
    @NotBlank(message = "密码不能为空")
    private String password;
    
    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
