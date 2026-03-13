package com.medihome.service;

import com.medihome.dto.LoginDTO;
import com.medihome.dto.RegisterDTO;
import com.medihome.entity.User;
import com.medihome.mapper.UserMapper;
import com.medihome.service.impl.UserServiceImpl;
import com.medihome.utils.JwtUtil;
import com.medihome.utils.SecurityUtil;
import com.medihome.vo.LoginVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * 用户服务单元测试
 */
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testLogin_Success() {
        // 准备测试数据
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPhone("13800138000");
        loginDTO.setPassword("123456");

        User user = new User();
        user.setId(1L);
        user.setPhone("13800138000");
        user.setPassword("encodedPassword");
        user.setRole(1);
        user.setStatus(1);

        when(userMapper.selectByPhone("13800138000")).thenReturn(user);
        when(securityUtil.matchesPassword("123456", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(1L, "13800138000", 1)).thenReturn("testToken");

        // 执行测试
        LoginVO result = userService.login(loginDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals("testToken", result.getToken());
        assertNotNull(result.getUser());
        assertEquals("13800138000", result.getUser().getPhone());
    }

    @Test
    void testLogin_UserNotFound() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPhone("13800138000");
        loginDTO.setPassword("123456");

        when(userMapper.selectByPhone("13800138000")).thenReturn(null);

        // 验证抛出异常
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.login(loginDTO);
        });
        assertEquals("用户不存在", exception.getMessage());
    }

    @Test
    void testLogin_WrongPassword() {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setPhone("13800138000");
        loginDTO.setPassword("wrongPassword");

        User user = new User();
        user.setPhone("13800138000");
        user.setPassword("encodedPassword");

        when(userMapper.selectByPhone("13800138000")).thenReturn(user);
        when(securityUtil.matchesPassword("wrongPassword", "encodedPassword")).thenReturn(false);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.login(loginDTO);
        });
        assertEquals("密码错误", exception.getMessage());
    }

    @Test
    void testRegister_Success() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPhone("13800138001");
        registerDTO.setPassword("123456");
        registerDTO.setVerifyCode("123456");

        when(userMapper.selectByPhone("13800138001")).thenReturn(null);
        when(securityUtil.encodePassword("123456")).thenReturn("encodedPassword");
        when(jwtUtil.generateToken(anyLong(), eq("13800138001"), eq(1))).thenReturn("testToken");

        LoginVO result = userService.register(registerDTO);

        assertNotNull(result);
        assertEquals("testToken", result.getToken());
        verify(userMapper).insert(any(User.class));
    }

    @Test
    void testRegister_PhoneAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setPhone("13800138000");
        registerDTO.setPassword("123456");
        registerDTO.setVerifyCode("123456");

        User existUser = new User();
        existUser.setPhone("13800138000");

        when(userMapper.selectByPhone("13800138000")).thenReturn(existUser);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.register(registerDTO);
        });
        assertEquals("手机号已注册", exception.getMessage());
    }
}
