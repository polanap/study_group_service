package org.example.study_group_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class StudyGroupServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyGroupServiceApplication.class, args);
    }

}
