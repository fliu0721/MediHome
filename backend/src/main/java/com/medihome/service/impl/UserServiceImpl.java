package com.medihome.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.medihome.dto.LoginDTO;
import com.medihome.dto.RegisterDTO;
import com.medihome.dto.UpdatePasswordDTO;
import com.medihome.entity.User;
import com.medihome.mapper.UserMapper;
import com.medihome.service.UserService;
import com.medihome.utils.JwtUtil;
import com.medihome.utils.SecurityUtil;
import com.medihome.vo.LoginVO;
import com.medihome.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Autowired
    private SecurityUtil securityUtil;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    private static final String VERIFY_CODE_PREFIX = "verify:code:";
    
    @Override
    public LoginVO register(RegisterDTO registerDTO) {
        // 验证验证码
        String codeKey = VERIFY_CODE_PREFIX + registerDTO.getPhone();
        String code = redisTemplate.opsForValue().get(codeKey);
        
        if (!"123456".equals(registerDTO.getVerifyCode()) && 
            (code == null || !code.equals(registerDTO.getVerifyCode()))) {
            throw new RuntimeException("验证码错误或已过期");
        }
        
        // 检查手机号是否已注册
        User existUser = baseMapper.selectByPhone(registerDTO.getPhone());
        if (existUser != null) {
            throw new RuntimeException("手机号已注册");
        }
        
        // 创建用户
        User user = new User();
        user.setPhone(registerDTO.getPhone());
        user.setPassword(securityUtil.encodePassword(registerDTO.getPassword()));
        user.setRole(1); // 普通用户
        user.setStatus(1); // 正常状态
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        
        baseMapper.insert(user);
        
        // 删除验证码
        redisTemplate.delete(codeKey);
        
        // 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getPhone(), user.getRole());
        
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUser(convertToVO(user));
        
        return loginVO;
    }
    
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 查询用户
        User user = baseMapper.selectByPhone(loginDTO.getPhone());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 验证密码
        if (!securityUtil.matchesPassword(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        // 检查状态
        if (user.getStatus() == 0) {
            throw new RuntimeException("账号已被禁用");
        }
        
        // 生成Token
        String token = jwtUtil.generateToken(user.getId(), user.getPhone(), user.getRole());
        
        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setUser(convertToVO(user));
        
        return loginVO;
    }
    
    @Override
    public void sendVerifyCode(String phone) {
        // 开发环境固定验证码
        String code = "123456";
        
        // 存储到Redis，5分钟过期
        redisTemplate.opsForValue().set(
            VERIFY_CODE_PREFIX + phone, 
            code, 
            5, 
            TimeUnit.MINUTES
        );
    }
    
    @Override
    public UserInfoVO getCurrentUserInfo(Long userId) {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return convertToVO(user);
    }
    
    @Override
    public void updateUserInfo(Long userId, String nickname, String avatar) {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (StringUtils.hasText(nickname)) {
            user.setNickname(nickname);
        }
        if (StringUtils.hasText(avatar)) {
            user.setAvatar(avatar);
        }
        user.setUpdateTime(LocalDateTime.now());
        
        baseMapper.updateById(user);
    }
    
    @Override
    public void updatePassword(Long userId, UpdatePasswordDTO updatePasswordDTO) {
        User user = baseMapper.selectById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        // 验证原密码
        if (!securityUtil.matchesPassword(updatePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("原密码错误");
        }
        
        // 更新密码
        user.setPassword(securityUtil.encodePassword(updatePasswordDTO.getNewPassword()));
        user.setUpdateTime(LocalDateTime.now());
        baseMapper.updateById(user);
    }
    
    private UserInfoVO convertToVO(User user) {
        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }
}
