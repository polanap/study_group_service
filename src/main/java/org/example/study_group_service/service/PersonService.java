package org.example.study_group_service.service;

import org.example.study_group_service.factory.PersonEntityFactory;
import org.example.study_group_service.models.dto.incomming.Person;
import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.repository.PersonRepository;
import org.example.study_group_service.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    PersonEntityFactory personEntityFactory;

    @Autowired
    StudyGroupRepository studyGroupRepository;

    public PersonEntity save(Person person) {
        return personRepository.save(personEntityFactory.create(person));
    }

    public PersonEntity findById(Long id) {
        return personRepository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        personRepository.deleteById(id);
    }

    public Page<PersonEntity> getAllFiltered(
            String name,
            String eyeColor,
            String hairColor,
            String nationality,
            PageRequest pageRequest
    ) {
        return personRepository.getPageFiltered(
                name == null ? "" : name,
                eyeColor  == null ? "" : eyeColor,
                hairColor   == null ? "" : hairColor,
                nationality  == null ? "" : nationality,
                pageRequest
        );
    }

    public void moveAllStudents(Long fromId, Long toId) {
        var fromGroup = studyGroupRepository.findById(fromId).orElseThrow();
        var toGroup = studyGroupRepository.findById(toId).orElseThrow();
        personRepository.moveAllStudents(fromGroup, toGroup);
    }

    public void enroll(Long personId, Long groupId) {
        var person =  personRepository.findById(personId).orElseThrow();
        var group =  studyGroupRepository.findById(groupId).orElseThrow();
        person.setGroup(group);
        personRepository.save(person);
    }

    public void unenroll(Long personId, Long groupId) {
        var person =  personRepository.findById(personId).orElseThrow();
        var group =  studyGroupRepository.findById(groupId).orElseThrow();
        if (person.getGroup().getId().equals(group.getId())) {
            person.setGroup(null);
            personRepository.save(person);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void getCountOfExpelledWithGroup() {

    }
}
