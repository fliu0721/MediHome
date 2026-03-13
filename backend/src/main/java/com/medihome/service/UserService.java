package com.medihome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.medihome.dto.LoginDTO;
import com.medihome.dto.RegisterDTO;
import com.medihome.dto.UpdatePasswordDTO;
import com.medihome.entity.User;
import com.medihome.vo.LoginVO;
import com.medihome.vo.UserInfoVO;

/**
 * 用户服务接口
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户注册
     */
    LoginVO register(RegisterDTO registerDTO);
    
    /**
     * 用户登录
     */
    LoginVO login(LoginDTO loginDTO);
    
    /**
     * 获取验证码
     */
    void sendVerifyCode(String phone);
    
    /**
     * 获取当前用户信息
     */
    UserInfoVO getCurrentUserInfo(Long userId);
    
    /**
     * 更新用户信息
     */
    void updateUserInfo(Long userId, String nickname, String avatar);
    
    /**
     * 修改密码
     */
    void updatePassword(Long userId, UpdatePasswordDTO updatePasswordDTO);
}
