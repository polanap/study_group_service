package org.example.study_group_service.security;

import org.example.study_group_service.models.entity.UserEntity;
import org.example.study_group_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


@Component
public class BasicAuthManager implements AuthenticationManager {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        if (authentication != null) {
            String username = (String) authentication.getPrincipal();
            String password = (String) authentication.getCredentials();

            UserEntity user = userService.findByUsername(username);
            if (user != null && userService.equalPasswords(user, password)) {
                return new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
                );
            } else {
                throw new AuthenticationCredentialsNotFoundException("");
            }
        } else {
            throw new AuthenticationCredentialsNotFoundException("Basic authentication failed");
        }
    }
}

