package org.example.study_group_service.repository;

import org.example.study_group_service.models.entity.PersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @Query(
            value = """
                SELECT *        
                FROM person p
                WHERE
                    (:name IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%')) 
                    OR (:eyeColor IS NULL OR LOWER(p.eye_color) LIKE CONCAT('%', LOWER(:eyeColor), '%'))
                    OR (:hairColor IS NULL OR LOWER(p.hair_color) LIKE CONCAT('%', LOWER(:hairColor), '%'))
                    OR (:nationality IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%'))
                    
                    """,
            countQuery = """
                SELECT
                    COUNT(*)
                FROM (
                    SELECT *        
                    FROM person p
                    WHERE
                        (:name IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%')) 
                        OR (:eyeColor IS NULL OR LOWER(p.eye_color) LIKE CONCAT('%', LOWER(:eyeColor), '%'))
                        OR (:hairColor IS NULL OR LOWER(p.hair_color) LIKE CONCAT('%', LOWER(:hairColor), '%'))
                        OR (:nationality IS NULL OR LOWER(p.name) LIKE CONCAT('%', LOWER(:name), '%'))
                ) as countQuery
        """,
            nativeQuery = true
    )
    Page<PersonEntity> getPageFiltered(
            String name,
            String eyeColor,
            String hairColor,
            String nationality,
            Pageable pageable
    );
}