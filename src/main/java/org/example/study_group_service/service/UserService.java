package org.example.study_group_service.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.study_group_service.models.dto.outcomming.PageDTO;
import org.example.study_group_service.models.dto.outcomming.UserDTO;
import org.example.study_group_service.models.dto.incomming.UserRegistration;
import org.example.study_group_service.models.Role;
import org.example.study_group_service.models.entity.UserEntity;
import org.example.study_group_service.repository.RoleRepository;
import org.example.study_group_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Getter
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    Logger log = LoggerFactory.getLogger(UserService.class);

    public PageDTO<UserDTO> getAllUsersPaginated(PageRequest pageRequest) {
        var page = userRepository.findAll(pageRequest);
        return new PageDTO<UserDTO>(page, page.getContent().stream().map(u -> new UserDTO(u)).toList());
    }

    public boolean equalPasswords(UserEntity user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    @Transactional
    public UserEntity save(UserRegistration request) {
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords not equals");
        }
        if (userRepository.getUserEntityByUsername(request.getUsername()) != null) {
            throw new IllegalArgumentException("User with this username already exist");
        }
        var user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRegistrationDate(LocalDateTime.now());
        var role = roleRepository.getRoleEntityByRole(Role.USER);
        user.addRole(role);
        roleRepository.save(role);
        return userRepository.save(user);
    }

    public void addAdminRole(Long userId) {
        addRole(userId, Role.ADMIN);
    }

    public void removeAdminRole(Long userId) {
        removeRole(userId, Role.ADMIN);
    }

    public void addRole(Long userId, Role role) {
        var roleEntity = roleRepository.getRoleEntityByRole(role);
        var user = getById(userId);
        user.addRole(roleEntity);
        userRepository.save(user);
    }

    public void removeRole(Long userId, Role role) {
        var roleEntity = roleRepository.getRoleEntityByRole(role);
        var user = getById(userId);
        user.removeRole(roleEntity);
        userRepository.save(user);
    }

    public boolean isAdmin(Long userId) {
        var user = getById(userId);
        return hasRole(userId, Role.ADMIN);
    }

    public boolean hasRole(Long userId, Role role) {
        var user = getById(userId);
        return user.getRoles().stream().map(roleEntity -> role).toList().contains(role);
    }

    public UserEntity getById(Long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.getUserEntityByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public UserEntity findUserById(Long userId) {
        Optional<UserEntity> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new UserEntity());
    }

    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


    public UserEntity getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.getUserEntityByUsername(username);
    }
}
