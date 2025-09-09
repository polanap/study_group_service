package org.example.study_group_service.web;

import org.example.study_group_service.models.dto.incomming.UserRegistration;
import org.example.study_group_service.models.dto.outcomming.ErrorMessageDTO;
import org.example.study_group_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;


    @PostMapping()
    public ResponseEntity addUser(@RequestBody UserRegistration request) {
        try{
            userService.save(request);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorMessageDTO(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
