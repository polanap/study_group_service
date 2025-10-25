package org.example.study_group_service.service;

import lombok.RequiredArgsConstructor;
import org.example.study_group_service.models.dto.incomming.UserLogin;
import org.example.study_group_service.models.dto.incomming.UserRegistration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public String signUp(UserRegistration request) {
        var user = userService.save(request);
        var jwt = jwtService.generateToken(user);
        return jwt;
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public String signIn(UserLogin request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return jwt;
    }
}