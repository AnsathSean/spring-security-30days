package com.ansathsean.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MeController {

    @GetMapping("/me")
    public Map<String, Object> me(@AuthenticationPrincipal OidcUser user) {
        // OidcUser 內含 ID Token claims + (可選) UserInfo
        return Map.of(
            "subject", user.getSubject(),               // sub
            "email", user.getEmail(),
            "email_verified", user.getEmailVerified(),
            "name", user.getFullName(),
            "claims", user.getClaims()                  // 全部 claims
        );
    }
}
