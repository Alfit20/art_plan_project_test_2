package com.example.art_plan_project_2.service;

import com.example.art_plan_project_2.dto.UserDTO;
import com.example.art_plan_project_2.entity.User;
import com.example.art_plan_project_2.exception.UserAlreadyRegisteredException;
import com.example.art_plan_project_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Login is not found"));
    }

    public UserDTO createUser(UserDTO userDTO) {
        if (userDTO.getLogin() == null) {
            log.info("Saving User {}", userDTO.getLogin());
            return UserDTO.from(userRepository.save(User.builder()
                    .login(userDTO.getLogin())
                    .password(userDTO.getPassword())
                    .build()));
        }
        throw new UserAlreadyRegisteredException();
    }
}
