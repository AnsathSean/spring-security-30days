package com.ansathsean.security;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class ScopeSecurity {
    public boolean hasScope(Authentication authentication, String requiredScope) {
        Object details = authentication.getDetails();
        if (details instanceof Map<?, ?> map) {
            String scope = (String) map.get("scope");
            return scope != null && scope.contains(requiredScope);
        }
        return false;
    }
}
