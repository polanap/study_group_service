package org.example.study_group_service.repository;

import org.example.study_group_service.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    UserEntity getUserEntityByUsername(String username);
}
