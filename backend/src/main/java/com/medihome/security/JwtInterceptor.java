package com.medihome.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.medihome.utils.JwtUtil;
import com.medihome.utils.UserContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * JWT 拦截器
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        
        if (token == null || !token.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        token = token.substring(7);
        
        try {
            DecodedJWT jwt = jwtUtil.verifyToken(token);
            Long userId = jwt.getClaim("userId").asLong();
            String phone = jwt.getClaim("phone").asString();
            Integer role = jwt.getClaim("role").asInt();
            
            UserContext.setUser(userId, phone, role);
            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserContext.clear();
    }
}
