package com.example.art_plan_project_2.service;

import com.example.art_plan_project_2.dto.UserDTO;
import com.example.art_plan_project_2.entity.User;
import com.example.art_plan_project_2.exception.UserAlreadyRegisteredException;
import com.example.art_plan_project_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService  {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserDTO createUser(UserDTO userDTO) {
        Optional<User> userLogin = userRepository.findUserByLogin(userDTO.getLogin());
        if (userLogin.isEmpty()) {
            log.info("Saving User {}", userDTO.getLogin());
            return UserDTO.from(userRepository.save(User.builder()
                    .login(userDTO.getLogin())
                    .password(passwordEncoder.encode(userDTO.getPassword()))
                    .build()));
        }
        throw new UserAlreadyRegisteredException();
    }
}
