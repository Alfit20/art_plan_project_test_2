package com.example.art_plan_project_2.service;

import com.example.art_plan_project_2.dto.AnimalDTO;
import com.example.art_plan_project_2.entity.Animal;
import com.example.art_plan_project_2.entity.User;
import com.example.art_plan_project_2.exception.AnimalWithThatNameAlreadyExists;
import com.example.art_plan_project_2.repository.AnimalRepository;
import com.example.art_plan_project_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;


    public AnimalDTO createAnimal(AnimalDTO animalDTO, User user) {
        Optional<Animal> animalName = animalRepository.findByName(animalDTO.getName());
        if (animalName.isEmpty()) {
            log.info("Saving Animal {}", animalDTO);
            return AnimalDTO.from(animalRepository.save(Animal.builder()
                    .dateOfBirth(animalDTO.getDateOfBirth())
                    .gender(animalDTO.getGender())
                    .name(animalDTO.getName())
                    .user(user)
                    .build()));
        }
        throw new AnimalWithThatNameAlreadyExists();
    }

    private User getUserByPrincipal(Authentication authentication) {
        String login = authentication.getName();
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Login not found with " + login));
    }
}
