package com.ansathsean.security;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.*;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter {
	   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	            throws IOException, ServletException {
	        HttpServletRequest httpReq = (HttpServletRequest) request;
	        HttpServletResponse httpRes = (HttpServletResponse) response;

	        String path = httpReq.getRequestURI();

	        // login 不攔
	        if ("/login".equals(path)) {
	            chain.doFilter(request, response);
	            return;
	        }

	        String authHeader = httpReq.getHeader("Authorization");
	        if (authHeader != null && authHeader.startsWith("Bearer ")) {
	            String token = authHeader.substring(7);
	            try {
	                if (JwtUtil.validateToken(token)) {
	                    chain.doFilter(request, response);
	                    return;
	                }
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }

	        httpRes.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        httpRes.getWriter().write("Invalid or missing JWT");
	    }
}
