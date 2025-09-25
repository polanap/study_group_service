package org.example.study_group_service.repository;

import org.example.study_group_service.models.entity.LocationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<LocationEntity, Long> {

    @Query(
            value = """
        SELECT l
        FROM LocationEntity l 
        WHERE
            (:name IS NULL OR LOWER(l.name) LIKE CONCAT('%', LOWER(:name), '%'))
    """,
            countQuery = """
        SELECT COUNT(l) 
        FROM LocationEntity l 
        WHERE
            (:name IS NULL OR LOWER(l.name) LIKE CONCAT('%', LOWER(:name), '%')) 
    """
    )
    Page<LocationEntity> getPageFiltered(
            String name,
            Pageable pageable
    );
}