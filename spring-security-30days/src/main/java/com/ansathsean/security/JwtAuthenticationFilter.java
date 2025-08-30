package com.ansathsean.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Jws<Claims> claimsJws = JwtUtil.validateToken(token);
                Claims claims = claimsJws.getBody();
                String username = claims.getSubject();
                String role = claims.get("role", String.class);
                String scope = claims.get("scope", String.class);

                System.out.println("✅ JWT 驗證成功");
                System.out.println("使用者: " + username);
                System.out.println("角色: " + role);
                System.out.println("Scope: " + scope);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority(role))
                        );

                authentication.setDetails(Map.of("scope", scope));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                e.printStackTrace(); // 👀 看清楚是哪個 Exception
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or Expired Token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}
