package com.ansathsean.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public String home() {
        return "這是公開頁面，任何人都能看到";
    }

    @GetMapping("/public")
    public String publicPage() {
        return "這也是公開 API";
    }

    @GetMapping("/user")
    public String userPage() {
        return "這是 USER 登入後才可以看到的頁面";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "這是 ADMIN 才能看到的頁面";
    }
}
