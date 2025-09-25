package org.example.study_group_service.service;

import org.example.study_group_service.factory.StudyGroupEntityFactory;
import org.example.study_group_service.models.dto.incomming.StudyGroup;
import org.example.study_group_service.models.entity.StudyGroupEntity;
import org.example.study_group_service.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudyGroupService {
    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    StudyGroupEntityFactory studyGroupEntityFactory;

    public StudyGroupEntity save(StudyGroup studyGroup) {
        return studyGroupRepository.save(studyGroupEntityFactory.create(studyGroup));
    }

    public StudyGroupEntity findById(Long id) {
        return studyGroupRepository.findById(id).orElseThrow();
    }

    public void deleteById(Long id) {
        studyGroupRepository.deleteById(id);
    }

    public Page<StudyGroupEntity> getAllFiltered(
            String name,
            String coordinates,
            String formOfEducation,
            String semesterEnum,
            PageRequest pageRequest
    ) {
        return studyGroupRepository.getPageFiltered(
                name == null ? "" : name,
                coordinates  == null ? "" : coordinates,
                formOfEducation   == null ? "" : formOfEducation,
                semesterEnum  == null ? "" : semesterEnum,
                pageRequest
        );
    }

    public Long countWithAverageMark(Float averageMark) {
        return studyGroupRepository.findAverageMarkCount(averageMark);
    }

    public StudyGroupEntity deleteRandomByAverageMark(Float averageMark) {
        var studyGroup = studyGroupRepository.getRandomWithAverageMark(averageMark);
        studyGroupRepository.delete(studyGroup);
        return studyGroup;
    }

    public void deleteRandomByAverageMarkWithoutEntityReturning(Float averageMark) {
        studyGroupRepository.deleteByAverageMarkCount(averageMark);
    }
}
