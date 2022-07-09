package com.example.art_plan_project_2.service;

import com.example.art_plan_project_2.entity.User;
import com.example.art_plan_project_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findUserByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("Username not found with username: " + username));
        return build(user);
    }


    public static User build(User user) {
        return new User(
                user.getId(),
                user.getLogin(),
                user.getPassword()
        );
    }

    public User loadUserById(Long userId) {
        return userRepository.findUserById(userId).orElseThrow();
    }
}
