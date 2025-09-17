package org.example.study_group_service.service;

import org.example.study_group_service.factory.PersonEntityFactory;
import org.example.study_group_service.models.dto.incomming.Person;
import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonEntityFactory personEntityFabric;

    public PersonEntity save(Person person) {
        return personRepository.save(personEntityFabric.create(person));
    }

    public PersonEntity findById(Long id) {
        return personRepository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public Page<PersonEntity> getAllPaginated(PageRequest pageRequest) {
        return personRepository.findAll(pageRequest);
    }
}
