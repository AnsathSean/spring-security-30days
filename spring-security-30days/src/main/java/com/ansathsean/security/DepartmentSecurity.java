package com.ansathsean.security;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class DepartmentSecurity {

    public boolean checkDepartment(Authentication authentication, String department) {
        String username = authentication.getName();

        // 模擬使用者的部門
        if ("admin".equals(username) && "FINANCE".equals(department)) {
            return true;
        } else if ("user".equals(username) && "IT".equals(department)) {
            return true;
        }
        return false;
    }
}
