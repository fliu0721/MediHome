package com.medihome.controller;

import com.medihome.dto.LoginDTO;
import com.medihome.dto.RegisterDTO;
import com.medihome.dto.UpdatePasswordDTO;
import com.medihome.service.UserService;
import com.medihome.utils.UserContext;
import com.medihome.vo.LoginVO;
import com.medihome.vo.ResultVO;
import com.medihome.vo.UserInfoVO;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResultVO<LoginVO> register(@Valid @RequestBody RegisterDTO registerDTO) {
        LoginVO loginVO = userService.register(registerDTO);
        return ResultVO.success(loginVO);
    }
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResultVO<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = userService.login(loginDTO);
        return ResultVO.success(loginVO);
    }
    
    /**
     * 获取验证码
     */
    @GetMapping("/verify-code")
    public ResultVO<Void> sendVerifyCode(@RequestParam String phone) {
        userService.sendVerifyCode(phone);
        return ResultVO.success();
    }
    
    /**
     * 获取当前用户信息
     */
    @GetMapping("/info")
    public ResultVO<UserInfoVO> getUserInfo() {
        UserInfoVO userInfo = userService.getCurrentUserInfo(UserContext.getUserId());
        return ResultVO.success(userInfo);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/info")
    public ResultVO<Void> updateUserInfo(@RequestBody Map<String, String> params) {
        userService.updateUserInfo(
            UserContext.getUserId(),
            params.get("nickname"),
            params.get("avatar")
        );
        return ResultVO.success();
    }
    
    /**
     * 修改密码
     */
    @PutMapping("/password")
    public ResultVO<Void> updatePassword(@Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        userService.updatePassword(UserContext.getUserId(), updatePasswordDTO);
        return ResultVO.success();
    }
}
