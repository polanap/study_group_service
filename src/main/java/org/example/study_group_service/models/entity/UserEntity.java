package org.example.study_group_service.models.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import static org.hibernate.internal.util.collections.CollectionHelper.setOf;

@Entity
@Table(name = "t_user")
@Data
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<RoleEntity> roles = setOf();

    @Column(name = "email")
    private String email;

    @Column(name = "last_access")
    private LocalDateTime lastAccess;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;


    public UserEntity() {
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void addRole(RoleEntity role) {
        synchronized (this.roles) {
            this.roles.add(role);
            role.getUsers().add(this);
        }
    }

    public void removeRole(RoleEntity role) {
        synchronized (this.roles) {
            this.roles.remove(role);
            role.getUsers().add(this);
        }
    }
}