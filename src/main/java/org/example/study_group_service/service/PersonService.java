package org.example.study_group_service.service;

import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public PersonEntity findById(Long id){
        return personRepository.findById(id).orElseThrow();
    }
}
