package org.example.study_group_service.repository;

import org.example.study_group_service.models.RequestStatus;
import org.example.study_group_service.models.entity.AdminRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRequestRepository extends JpaRepository<AdminRequestEntity, Integer>{
    AdminRequestEntity findAdminRequestEntityByUserId(Integer userId);

    Page<AdminRequestEntity> findAllByStatus(RequestStatus status, Pageable pageable);
}