package com.medihome.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 安全工具类单元测试
 */
public class SecurityUtilTest {

    private final SecurityUtil securityUtil = new SecurityUtil();

    @Test
    void testEncodePassword() {
        String password = "123456";
        String encoded = securityUtil.encodePassword(password);
        
        assertNotNull(encoded);
        assertNotEquals(password, encoded);
    }

    @Test
    void testMatchesPassword_Correct() {
        String password = "123456";
        String encoded = securityUtil.encodePassword(password);
        
        boolean matches = securityUtil.matchesPassword(password, encoded);
        assertTrue(matches);
    }

    @Test
    void testMatchesPassword_Wrong() {
        String password = "123456";
        String encoded = securityUtil.encodePassword(password);
        
        boolean matches = securityUtil.matchesPassword("wrongPassword", encoded);
        assertFalse(matches);
    }
}
