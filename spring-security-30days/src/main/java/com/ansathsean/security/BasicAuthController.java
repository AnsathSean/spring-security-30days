package com.ansathsean.security;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicAuthController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, you are authenticated!";
    }
	
    @GetMapping("/manual-basic-auth")
    public String checkAuth(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring("Basic ".length());
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);

            // credentials = "username:password"
            String[] values = credentials.split(":", 2);
            String username = values[0];
            String password = values[1];

            if ("ithome".equals(username) && "secret".equals(password)) {
                return "Manual auth success!";
            }
        }
        return "Unauthorized";
    }
}
