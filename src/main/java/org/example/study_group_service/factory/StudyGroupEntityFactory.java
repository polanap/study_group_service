package org.example.study_group_service.factory;

import org.example.study_group_service.models.dto.incomming.StudyGroup;
import org.example.study_group_service.models.entity.StudyGroupEntity;
import org.example.study_group_service.repository.CoordinatesRepository;
import org.example.study_group_service.repository.LocationRepository;
import org.example.study_group_service.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StudyGroupFactory {

    @Autowired
    private CoordinatesRepository coordinatesRepository;

    @Autowired
    private PersonRepository personRepository;

    public StudyGroupEntity create (StudyGroup studyGroup) {
        StudyGroupEntity studyGroupEntity = new StudyGroupEntity(studyGroup);
        if (studyGroup.getGroupAdminId() != null) {
            studyGroupEntity.setGroupAdmin(personRepository.findById(studyGroup.getGroupAdminId()).orElse(null));
        }
        if (studyGroup.getCoordinatesId() != null) {
            studyGroupEntity.setCoordinates(coordinatesRepository.findById(studyGroup.getCoordinatesId()).orElse(null));
        }
        return studyGroupEntity;
    }
}
