package org.example.study_group_service.web;

import lombok.RequiredArgsConstructor;
import org.example.study_group_service.models.dto.incomming.UserRegistration;
import org.example.study_group_service.service.AuthenticationService;
import org.example.study_group_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/registration")
@Validated
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public String addUser(@Valid @RequestBody UserRegistration request) {
        return authenticationService.signUp(request);
    }

}
