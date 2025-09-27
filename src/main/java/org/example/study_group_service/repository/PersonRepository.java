package org.example.study_group_service.repository;

import org.example.study_group_service.models.entity.PersonEntity;
import org.example.study_group_service.models.entity.StudyGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query(
            value = """
        SELECT p 
        FROM PersonEntity p 
        WHERE
            (:name IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%')) 
            AND (:eyeColor IS NULL OR LOWER(p.eyeColor) LIKE CONCAT('%', LOWER(:eyeColor), '%'))
            AND (:hairColor IS NULL OR LOWER(p.hairColor) LIKE CONCAT('%', LOWER(:hairColor), '%'))
            AND (:nationality IS NULL OR LOWER(p.nationality) LIKE CONCAT('%', LOWER(:nationality), '%'))
    """,
            countQuery = """
        SELECT COUNT(p) 
        FROM PersonEntity p 
        WHERE
            (:name IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%')) 
            AND (:eyeColor IS NULL OR LOWER(p.eyeColor) LIKE CONCAT('%', LOWER(:eyeColor), '%'))
            AND (:hairColor IS NULL OR LOWER(p.hairColor) LIKE CONCAT('%', LOWER(:hairColor), '%'))
            AND (:nationality IS NULL OR LOWER(p.nationality) LIKE CONCAT('%', LOWER(:nationality), '%'))
    """
    )
    Page<PersonEntity> getPageFiltered(
            String name,
            String eyeColor,
            String hairColor,
            String nationality,
            Pageable pageable
    );

    @Modifying
    @Query(value = """
    UPDATE PersonEntity SET group = :toGroup WHERE group = :fromGroup
    """
    )
    void moveAllStudents(StudyGroupEntity toGroup, StudyGroupEntity fromGroup);


    @Query("""
    SELECT COUNT(p) FROM PersonEntity p WHERE p.isExpelled AND p.group IS NOT NULL
""")
    void getCountOfExpelledWithGroup();

}