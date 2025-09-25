package org.example.study_group_service.web;

import org.example.study_group_service.models.dto.incomming.UserLogin;
import org.example.study_group_service.security.BasicAuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private BasicAuthManager authenticationManager;

    @PostMapping
    public void login(@RequestBody UserLogin loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}

