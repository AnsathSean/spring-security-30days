package com.ansathsean.security;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

    // ✅ JWT 登入
    @PostMapping("/api/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if ("admin".equals(username) && "password".equals(password)) {
            String accessToken = JwtUtil.generateAccessToken(username, "ROLE_ADMIN");
            return Map.of("accessToken", accessToken);
        } else if ("user".equals(username) && "password".equals(password)) {
            String accessToken = JwtUtil.generateAccessToken(username, "ROLE_USER");
            return Map.of("accessToken", accessToken);
        }
        return Map.of("error", "帳號或密碼錯誤");
    }

    // ✅ 測試 JWT
    @GetMapping("/hello-jwt")
    public String helloJwt() {
        return "Hello, JWT 使用者：" +
                SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // ✅ 測試 OIDC
    @GetMapping("/hello-oidc")
    public String helloOidc(@AuthenticationPrincipal OidcUser oidcUser) {
        return "Hello, OIDC 使用者：" + oidcUser.getFullName() + " (" + oidcUser.getEmail() + ")";
    }

    // ✅ RBAC: 只有 ADMIN 才能進
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminOnly() {
        return "這是管理員專區 (RBAC)";
    }

    // ✅ RBAC: 只有 USER 才能進
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userOnly() {
        return "這是一般使用者專區 (RBAC)";
    }

    // ✅ ABAC: 只有部門=HR 才能進
    @GetMapping("/department/hr")
    @PreAuthorize("@departmentSecurity.checkDepartment(authentication, 'HR')")
    public String hrDepartment() {
        return "這是 HR 部門專區 (ABAC)";
    }

    // ✅ ABAC: 只有部門=IT 才能進
    @GetMapping("/department/it")
    @PreAuthorize("@departmentSecurity.checkDepartment(authentication, 'IT')")
    public String itDepartment() {
        return "這是 IT 部門專區 (ABAC)";
    }
}
