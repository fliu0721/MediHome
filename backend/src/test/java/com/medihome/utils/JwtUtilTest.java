package com.medihome.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JWT工具类单元测试
 */
public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "test-secret-key");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86400000L); // 1天
    }

    @Test
    void testGenerateAndVerifyToken() {
        Long userId = 1L;
        String phone = "13800138000";
        Integer role = 1;

        String token = jwtUtil.generateToken(userId, phone, role);
        assertNotNull(token);

        DecodedJWT jwt = jwtUtil.verifyToken(token);
        assertNotNull(jwt);
        assertEquals(userId, jwt.getClaim("userId").asLong());
        assertEquals(phone, jwt.getClaim("phone").asString());
        assertEquals(role, jwt.getClaim("role").asInt());
    }

    @Test
    void testGetUserIdFromToken() {
        String token = jwtUtil.generateToken(1L, "13800138000", 1);
        Long userId = jwtUtil.getUserIdFromToken(token);
        assertEquals(1L, userId);
    }

    @Test
    void testGetPhoneFromToken() {
        String token = jwtUtil.generateToken(1L, "13800138000", 1);
        String phone = jwtUtil.getPhoneFromToken(token);
        assertEquals("13800138000", phone);
    }

    @Test
    void testGetRoleFromToken() {
        String token = jwtUtil.generateToken(1L, "13800138000", 1);
        Integer role = jwtUtil.getRoleFromToken(token);
        assertEquals(1, role);
    }
}
