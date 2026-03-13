package com.medihome.vo;

import lombok.Data;

/**
 * 用户信息VO
 */
@Data
public class UserInfoVO {
    
    private Long id;
    private String phone;
    private String nickname;
    private String avatar;
    private Integer role;
}
