package org.example.study_group_service.repository;

import org.example.study_group_service.models.entity.StudyGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudyGroupRepository extends JpaRepository<StudyGroupEntity, Long> {

    @Query(
            value = """
        SELECT sg 
        FROM StudyGroupEntity sg 
        WHERE
            (:name IS NULL OR LOWER(sg.name) LIKE CONCAT('%', LOWER(:name), '%'))
            AND (:eyeColor IS NULL OR LOWER(sg.coordinates) LIKE CONCAT('%', LOWER(:coordinates), '%'))
            AND (:hairColor IS NULL OR LOWER(sg.formOfEducation) LIKE CONCAT('%', LOWER(:formOfEducation), '%'))
            AND (:nationality IS NULL OR LOWER(sg.semesterEnum) LIKE CONCAT('%', LOWER(:semesterEnum), '%'))
    """,
            countQuery = """
        SELECT COUNT(sg) 
        FROM StudyGroupEntity sg 
        WHERE
            (:name IS NULL OR LOWER(sg.name) LIKE CONCAT('%', LOWER(:name), '%'))
            AND (:eyeColor IS NULL OR LOWER(sg.coordinates) LIKE CONCAT('%', LOWER(:coordinates), '%'))
            AND (:hairColor IS NULL OR LOWER(sg.formOfEducation) LIKE CONCAT('%', LOWER(:formOfEducation), '%'))
            AND (:nationality IS NULL OR LOWER(sg.semesterEnum) LIKE CONCAT('%', LOWER(:semesterEnum), '%'))
    """
    )
    Page<StudyGroupEntity> getPageFiltered(
            String name,
            String coordinates,
            String formOfEducation,
            String semesterEnum,
            Pageable pageable
    );
}