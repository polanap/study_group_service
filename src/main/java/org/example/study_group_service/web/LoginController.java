package org.example.study_group_service.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.study_group_service.models.dto.incomming.UserLogin;
import org.example.study_group_service.service.AuthenticationService;
import org.example.study_group_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/login")
@Validated
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<String> login(@Valid @RequestBody UserLogin credentials, HttpServletRequest request) {
        String token = authenticationService.signIn(credentials);
        return ResponseEntity.ok(token);
    }

}
