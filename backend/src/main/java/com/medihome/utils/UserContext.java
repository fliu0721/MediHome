package com.medihome.utils;

/**
 * 用户上下文（ThreadLocal存储当前登录用户）
 */
public class UserContext {
    
    private static final ThreadLocal<Long> USER_ID_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<String> USER_PHONE_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<Integer> USER_ROLE_HOLDER = new ThreadLocal<>();
    
    public static void setUser(Long userId, String phone, Integer role) {
        USER_ID_HOLDER.set(userId);
        USER_PHONE_HOLDER.set(phone);
        USER_ROLE_HOLDER.set(role);
    }
    
    public static Long getUserId() {
        return USER_ID_HOLDER.get();
    }
    
    public static String getUserPhone() {
        return USER_PHONE_HOLDER.get();
    }
    
    public static Integer getUserRole() {
        return USER_ROLE_HOLDER.get();
    }
    
    public static void clear() {
        USER_ID_HOLDER.remove();
        USER_PHONE_HOLDER.remove();
        USER_ROLE_HOLDER.remove();
    }
}
