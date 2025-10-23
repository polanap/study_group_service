package org.example.study_group_service.web;

import org.example.study_group_service.models.dto.incomming.UserRegistration;
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
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public void addUser(@Valid @RequestBody UserRegistration request) {
        userService.save(request);
    }

}
