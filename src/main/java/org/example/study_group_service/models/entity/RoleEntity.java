package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.study_group_service.models.Role;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "t_role")
@Data
public class RoleEntity implements GrantedAuthority {
    @Id
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleEntity() {
    }

    public RoleEntity(Integer id) {
        this.id = id;
    }

    public RoleEntity(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return getRole().toString();
    }
    
}