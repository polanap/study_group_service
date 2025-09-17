package org.example.study_group_service.repository;

import org.example.study_group_service.models.Role;
import org.example.study_group_service.models.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity getRoleEntityByRole(Role role);
}