package org.example.study_group_service.service;

import lombok.Getter;
import org.example.study_group_service.models.dto.outcomming.PageDTO;
import org.example.study_group_service.models.dto.outcomming.UserDTO;
import org.example.study_group_service.models.dto.incomming.UserRegistration;
import org.example.study_group_service.models.Role;
import org.example.study_group_service.models.entity.UserEntity;
import org.example.study_group_service.repository.RoleRepository;
import org.example.study_group_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Getter
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public PageDTO<UserDTO> getAllUsersPaginated(PageRequest pageRequest) {
        var page = userRepository.findAll(pageRequest);
        return new PageDTO<UserDTO>(page, page.getContent().stream().map(u -> new UserDTO(u)).toList());
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

    public void addAdminRole(int userId) {
        addRole(userId, Role.ADMIN);
    }

    public void removeAdminRole(int userId) {
        removeRole(userId, Role.ADMIN);
    }

    public void addRole(int userId, Role role) {
        var roleEntity = roleRepository.getRoleEntityByRole(role);
        var user = getById(userId);
        user.addRole(roleEntity);
        userRepository.save(user);
    }

    public void removeRole(int userId, Role role) {
        var roleEntity = roleRepository.getRoleEntityByRole(role);
        var user = getById(userId);
        user.removeRole(roleEntity);
        userRepository.save(user);
    }

    public boolean isAdmin(int userId) {
        var user = getById(userId);
        return hasRole(userId, Role.ADMIN);
    }

    public boolean hasRole(int userId, Role role) {
        var user = getById(userId);
        return user.getRoles().stream().map(roleEntity -> role).toList().contains(role);
    }

    public UserEntity getById(int userId) {
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

    public UserEntity findUserById(Integer userId) {
        Optional<UserEntity> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new UserEntity());
    }

    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUser(Integer userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


    public UserEntity getUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

}
