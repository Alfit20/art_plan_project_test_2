package com.example.art_plan_project_2.service;

import com.example.art_plan_project_2.dto.AnimalDTO;
import com.example.art_plan_project_2.entity.Animal;
import com.example.art_plan_project_2.entity.User;
import com.example.art_plan_project_2.exception.AnimalNotFoundException;
import com.example.art_plan_project_2.exception.AnimalWithThatNameAlreadyExists;
import com.example.art_plan_project_2.repository.AnimalRepository;
import com.example.art_plan_project_2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;


    public AnimalDTO createAnimal(AnimalDTO animalDTO, User user) {
        if (checkAnimalName(animalDTO.getName())) {
            throw new AnimalWithThatNameAlreadyExists();
        }
        log.info("Saving animal {}", animalDTO);
        return AnimalDTO.from(animalRepository.save(Animal.builder()
                .dateOfBirth(animalDTO.getDateOfBirth())
                .gender(animalDTO.getGender())
                .name(animalDTO.getName())
                .user(user)
                .build()));

    }

    public void deleteAnimal(Long animalId, User currentUser) {
        Animal animal = animalRepository.findById(animalId).orElseThrow();
        User user = animal.getUser();
        if (!user.equals(currentUser)) {
            throw new NoSuchElementException();
        }
        log.info("Delete animal {}", animal);
        animalRepository.delete(animal);
    }

    public AnimalDTO updateAnimal(Long id, AnimalDTO animalDTO, User currentUser) {
        Animal animal = animalRepository.findById(id).orElseThrow();
        User user = animal.getUser();
        if (!user.equals(currentUser)) {
            throw new NoSuchElementException();
        }
        if (checkAnimalName(animalDTO.getName())) {
            throw new AnimalWithThatNameAlreadyExists();
        }
        animal.setName(animalDTO.getName());
        animal.setGender(animalDTO.getGender());
        animal.setDateOfBirth(animalDTO.getDateOfBirth());
        animalRepository.save(animal);
        return AnimalDTO.from(animal);
    }

    public List<AnimalDTO> getAnimals(User user) {
        return animalRepository.findByUser(user).stream()
                .map(AnimalDTO::from)
                .collect(Collectors.toList());
    }

    public boolean checkAnimalName(String name) {
        return animalRepository.existsByName(name);
    }

    public AnimalDTO getOneAnimal(Long animalId) {
        return AnimalDTO.from(animalRepository.findById(animalId)
                .orElseThrow(AnimalNotFoundException::new));
    }
}
