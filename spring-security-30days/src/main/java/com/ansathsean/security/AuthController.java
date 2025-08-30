package com.ansathsean.security;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

    // ✅ JWT 登入
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if ("admin".equals(username) && "password".equals(password)) {
            String accessToken = JwtUtil.generateAccessToken(username, "ROLE_ADMIN");
            return Map.of("accessToken", accessToken);
        }
        return Map.of("error", "帳號或密碼錯誤");
    }

    // ✅ 測試 JWT API
    @GetMapping("/hello-jwt")
    public String helloJwt() {
        return "Hello, JWT 使用者：" +
                SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // ✅ 測試 OIDC API
    @GetMapping("/hello-oidc")
    public String helloOidc(@AuthenticationPrincipal OidcUser oidcUser) {
        return "Hello, OIDC 使用者：" + oidcUser.getFullName() + " (" + oidcUser.getEmail() + ")";
    }
}
