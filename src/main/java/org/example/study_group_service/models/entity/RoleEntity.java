package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import org.example.study_group_service.models.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "t_role")
public class RoleEntity implements GrantedAuthority {
    @Id
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Transient
    @ManyToMany(mappedBy = "roles")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Set<UserEntity> users;

    public RoleEntity() {
    }

    public RoleEntity(Integer id) {
        this.id = id;
    }

    public RoleEntity(Integer id, Role role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getRole().toString();
    }
}