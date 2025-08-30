package com.ansathsean.security;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class AuthController {

	    // JWT 登入，admin 有 ROLE_ADMIN + read,write scope，user 有 ROLE_USER + read scope
	    @PostMapping("/api/login")
	    public Map<String, String> login(@RequestBody Map<String, String> request) {
	        String username = request.get("username");
	        String password = request.get("password");
	
	        if ("admin".equals(username) && "password".equals(password)) {
	            String token = JwtUtil.generateAccessToken(username, "ROLE_ADMIN", "read write");
	            return Map.of("accessToken", token);
	        } else if ("user".equals(username) && "password".equals(password)) {
	            String token = JwtUtil.generateAccessToken(username, "ROLE_USER", "read");
	            return Map.of("accessToken", token);
	        }
	        return Map.of("error", "帳號或密碼錯誤");
	    }


	    @GetMapping("/hello-jwt")
	    public String helloJwt() {
	        return "Hello, JWT 使用者：" +
	                SecurityContextHolder.getContext().getAuthentication().getName();
	    }

	    @GetMapping("/hello-oidc")
	    public String helloOidc(@AuthenticationPrincipal OidcUser oidcUser) {
	        return "Hello, OIDC 使用者：" + oidcUser.getFullName() + " (" + oidcUser.getEmail() + ")";
	    }

	    // RBAC: 只有 ROLE_ADMIN 可進
	    @GetMapping("/admin")
	    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
	    public String adminOnly() {
	        return "這是管理員專區 (RBAC)";
	    }

	    // ABAC: Finance 部門才可看
	    @GetMapping("/finance/report")
	    @PreAuthorize("hasAuthority('ROLE_ADMIN') and @scopeSecurity.hasScope(authentication, 'read') and @departmentSecurity.checkDepartment(authentication, 'FINANCE')")
	    public String financeReport() {
	        return "這是財務報表，只有 Finance 部門 + Admin + 有 read scope 才能看 (Scope+Role+Attribute)";
	    }
	}

