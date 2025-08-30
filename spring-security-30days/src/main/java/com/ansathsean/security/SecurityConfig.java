package com.ansathsean.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        		.formLogin(form -> form.disable()) 
				.csrf(csrf -> csrf.disable())    
                .authorizeHttpRequests(auth -> auth
                				.requestMatchers("/", "/login", "/login-with-refresh").permitAll()
                                .anyRequest().authenticated()
                )
                .oauth2Login(withDefaults()) // 啟用 OIDC Login
        		.addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // ✅ 本地 JWT 登入

        return http.build();
    }
}
