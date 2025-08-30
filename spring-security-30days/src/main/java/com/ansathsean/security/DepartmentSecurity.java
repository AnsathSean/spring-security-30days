package com.ansathsean.security;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class DepartmentSecurity {

    // 模擬從 JWT 或 DB 讀取部門屬性
    public boolean checkDepartment(Authentication authentication, String department) {
        String username = authentication.getName();

        // 假設 admin 屬於 HR 部門, user 屬於 IT 部門
        if ("admin".equals(username) && "HR".equals(department)) {
            return true;
        } else if ("user".equals(username) && "IT".equals(department)) {
            return true;
        }
        return false;
    }
}
