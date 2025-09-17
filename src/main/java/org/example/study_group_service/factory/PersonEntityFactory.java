package org.example.study_group_service.fabric;

import org.example.study_group_service.models.dto.incomming.Person;
import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class PersonEntityFabric {

    @Autowired
    private LocationRepository locationRepository;

    public PersonEntity create(Person person){
        var personEntity = new PersonEntity(person);
        if (person.getLocationId() != null) {
            personEntity.setLocation(locationRepository.findById(person.getLocationId()).orElseThrow());
        }
        return personEntity;
    }
}
