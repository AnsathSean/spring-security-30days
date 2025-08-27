package com.ansathsean.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
public class SecurityConfig {

    @Bean
    UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withUsername("ithome")
                .password("{noop}secret") // {noop} 表示密碼不加密
                .roles("USER")
                .build()
        );
    }

    // 安全性過濾器
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // 為了測試方便先關掉 CSRF
                .authorizeHttpRequests(auth -> auth
                                .anyRequest().authenticated())
                .httpBasic(withDefaults()); // 啟用 Basic Authentication

        return http.build();
    }
}
