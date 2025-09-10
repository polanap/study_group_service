package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.example.study_group_service.models.Role;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@Entity
@Table(name = "t_role")
@Data
public class RoleEntity implements GrantedAuthority {
    @Id
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = setOf();

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

    public void addUser(UserEntity user) {
        synchronized (this.users) {
            this.users.add(user);
            user.getRoles().add(this);
        }
    }

    public void removeUser(UserEntity user) {
        synchronized (this.users) {
            this.users.remove(user);
            user.getRoles().add(this);
        }
    }
}