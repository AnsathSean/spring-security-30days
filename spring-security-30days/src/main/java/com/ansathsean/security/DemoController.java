package com.ansathsean.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public/hello")
    public String publicHello() { return "Hello (public)"; }

    
    @GetMapping("/api/profile")
    public String profile() { return "This is a protected resource (Basic Auth)"; }
}