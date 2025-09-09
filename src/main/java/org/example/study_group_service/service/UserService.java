package org.example.study_group_service.service;

import lombok.Getter;
import org.example.study_group_service.models.dto.outcomming.PageDTO;
import org.example.study_group_service.models.dto.outcomming.UserDTO;
import org.example.study_group_service.models.dto.incomming.UserRegistration;
import org.example.study_group_service.models.Role;
import org.example.study_group_service.models.entity.RoleEntity;
import org.example.study_group_service.models.entity.UserEntity;
import org.example.study_group_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Getter
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public PageDTO<UserDTO> allUsersPaginated(PageRequest pageRequest){
        var page = userRepository.findAll(pageRequest);
        return new PageDTO<UserDTO>(page, page.getContent().stream().map(u -> new UserDTO(u)).toList());
    }

    public UserEntity save(UserRegistration request) {
        var user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);

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

    public UserEntity saveUser(UserRegistration request) throws ValidationException {
        if (request.getUsername()==null){
            throw new ValidationException("Username is required");
        }
        if (request.getPassword()==null){
            throw new ValidationException("Password is required");
        }
        if (request.getConfirmPassword()==null){
            throw new ValidationException("Confirm password is required");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())){
            throw new ValidationException("Passwords not equals");
        }

        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new ValidationException("User with this username already exist");
        }
        if (request.getUsername().length()<3 && request.getUsername().length()>30){
            throw new ValidationException("username must be less than or equal to 30");
        }
        if (request.getPassword().length()<3 && request.getPassword().length()>30){
            throw new ValidationException("password must be less than or equal to 30");
        }

        UserEntity user = new UserEntity();
        user.setRoles(Collections.singleton(new RoleEntity(1, Role.USER)));
        user.setUsername(request.getUsername());
        user.setPassword(encoder.encode(request.getPassword()));
        user.setRegistrationDate(LocalDateTime.now());
        return userRepository.save(user);

    }

    public boolean deleteUser(Integer userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


    public UserEntity getUser(Integer id){
        return userRepository.findById(id).orElse(null);
    }

}
