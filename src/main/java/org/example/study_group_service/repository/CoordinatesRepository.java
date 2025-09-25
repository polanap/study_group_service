package org.example.study_group_service.repository;

import org.example.study_group_service.models.entity.CoordinatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends JpaRepository<CoordinatesEntity, Long> {


}