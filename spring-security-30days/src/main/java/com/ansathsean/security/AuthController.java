package com.ansathsean.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;

import java.util.Map;

@RestController
public class AuthController {
	
	// Day12: 保留舊的登入方式（只回 Access Token）
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if ("admin".equals(username) && "password".equals(password)) {
            return JwtUtil.generateToken(username, "ROLE_ADMIN");
        }
        return "帳號或密碼錯誤";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, " + 
            (org.springframework.security.core.context.SecurityContextHolder.getContext()
                .getAuthentication().getName());
    }
    
    // Day13: 新增支援 Refresh Token 的登入方式
    @PostMapping("/login-with-refresh")
    public ResponseEntity<?> loginWithRefresh(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if ("admin".equals(username) && "password".equals(password)) {
            String accessToken = JwtUtil.generateAccessToken(username, "ROLE_ADMIN");
            String refreshToken = JwtUtil.generateRefreshToken(username);

            return ResponseEntity.ok(Map.of(
                    "accessToken", accessToken,
                    "refreshToken", refreshToken
            ));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "帳號或密碼錯誤"));
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        try {
            // 驗證 Refresh Token
            Jws<Claims> claims = JwtUtil.validateToken(refreshToken);
            String username = claims.getBody().getSubject();

            // 這裡可以加上角色資訊，或直接重新查資料庫
            String newAccessToken = JwtUtil.generateAccessToken(username, "ROLE_ADMIN");

            // ✅ 回傳新的 Access Token（可選擇是否更新 Refresh Token）
            return ResponseEntity.ok(Map.of(
                    "accessToken", newAccessToken,
                    "refreshToken", refreshToken // 可以重複使用原本的 refresh，或換新
            ));

        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Refresh Token 過期，請重新登入"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Refresh Token 無效"));
        }
    }


}
