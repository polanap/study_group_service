package org.example.study_group_service.repository;

import org.example.study_group_service.models.entity.StudyGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("""
        SELECT COUNT (sg)
        FROM StudyGroupEntity sg
        WHERE sg.averageMark > :averageMark
    """
    )
    Long findAverageMarkCount(Float averageMark);

    @Modifying
    @Query(
        value = """
        DELETE FROM study_groupEntity sg
        WHERE sg.id IN (
                SELECT sgt.id
                FROM study_groupEntity sgt
                WHERE sgt.average_mark = :averageMark
                LIMIT 1
            )
    """,
            nativeQuery = true
    )
    void deleteByAverageMarkCount(Float averageMark);

    @Query(
            value = """
        SELECT sg.id
                FROM study_groupEntity sg
                WHERE sg.average_mark = :averageMark
                LIMIT 1
    """,
            nativeQuery = true
    )
    StudyGroupEntity getRandomWithAverageMark(Float averageMark);

    @Query("""
    SELECT SUM(sg.expelledStudents) FROM StudyGroupEntity sg
""")
    Long getCountOfExpelled();
}