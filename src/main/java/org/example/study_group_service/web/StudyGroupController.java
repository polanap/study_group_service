package org.example.study_group_service.web;

import org.example.study_group_service.models.SortOrder;
import org.example.study_group_service.models.dto.incomming.StudyGroup;
import org.example.study_group_service.models.dto.outcomming.CountDTO;
import org.example.study_group_service.models.entity.StudyGroupEntity;
import org.example.study_group_service.service.StudyGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.hibernate.internal.util.collections.CollectionHelper.listOf;

@RestController
@RequestMapping("/study-group")
public class StudyGroupController {
    @Autowired
    private StudyGroupService studyGroupService;

    @DeleteMapping
    public void deleteById(@RequestParam Long id){
        studyGroupService.deleteById(id);
    }

    @PostMapping
    public void save(@RequestBody StudyGroup studyGroup){
        studyGroupService.save(studyGroup);
    }

    @GetMapping
    public StudyGroupEntity findById(@RequestParam Long id){
        return studyGroupService.findById(id);
    }

    @GetMapping("/filtered")
    public Page<StudyGroupEntity> getAllFiltered(
            @RequestParam(required=false) String name,
            @RequestParam(required=false) String coordinates,
            @RequestParam(required=false) String formOfEducation,
            @RequestParam(required=false) String semesterEnum,
            @RequestParam(required=false) SortOrder byName,
            @RequestParam(required=false) SortOrder byCoordinates,
            @RequestParam(required=false) SortOrder byFormOfEducation,
            @RequestParam(required=false) SortOrder bySemesterEnum,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return studyGroupService.getAllFiltered(
                name,
                coordinates,
                formOfEducation,
                semesterEnum,
                PageRequest.of(
                        page,
                        size,
                        Sort.by(
                                listOf(
                                        byName != null ? byName.toSpringSortOrder("name") : null,
                                        byCoordinates != null ? byCoordinates.toSpringSortOrder("eyeColor") : null,
                                        byFormOfEducation != null ? byFormOfEducation.toSpringSortOrder("hairColor") : null,
                                        bySemesterEnum != null ? bySemesterEnum.toSpringSortOrder("nationality") : null
                                ).stream().filter(Objects::nonNull).toList()
                        )
                )
        );
    }

    @GetMapping("/count/greater-than")
    public CountDTO findAverageMarkCount(@RequestParam Float averageMark){
        return new CountDTO(studyGroupService.countWithAverageMark(averageMark));
    }

    @DeleteMapping("/by-average-mark")
    public StudyGroupEntity deleteByAverageMark(@RequestParam Float averageMark){
        return studyGroupService.deleteRandomByAverageMark(averageMark);
    }

    @GetMapping("/expelled")
    public CountDTO findCountOfExpelled(){
        return new CountDTO(studyGroupService.getExpelledCount());
    }

}
