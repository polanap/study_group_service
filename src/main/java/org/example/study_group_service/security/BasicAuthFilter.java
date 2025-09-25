package org.example.study_group_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;

import java.io.IOException;

public class BasicAuthFilter extends AuthenticationFilter {

    public BasicAuthFilter(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            super.doFilterInternal(request, response, chain);
        } catch (AuthenticationException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed: " + e.getMessage());
            return;
        }
        chain.doFilter(request, response);
    }


}

