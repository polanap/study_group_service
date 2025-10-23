package org.example.study_group_service.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.example.study_group_service.models.dto.incomming.UserLogin;
import org.example.study_group_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/login")
@Validated
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> login(@Valid @RequestBody UserLogin credentials, HttpServletRequest request) {
        String token = userService.authenticate(credentials);
        return ResponseEntity.ok(token);
    }

}
