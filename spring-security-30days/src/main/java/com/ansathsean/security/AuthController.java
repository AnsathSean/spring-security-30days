package com.ansathsean.security;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> request) throws Exception {
        String username = request.get("username");
        String password = request.get("password");

        // 模擬帳密驗證
        if ("admin".equals(username) && "password".equals(password)) {
            return JwtUtil.generateToken(Map.of(
                    "sub", username,
                    "role", "admin",
                    "iat", System.currentTimeMillis() / 1000
            ));
        }
        return "帳號或密碼錯誤";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, JWT!";
    }
}
