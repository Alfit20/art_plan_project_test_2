package com.example.art_plan_project_2.controller;

import com.example.art_plan_project_2.dto.AnimalDTO;
import com.example.art_plan_project_2.entity.User;
import com.example.art_plan_project_2.exception.AnimalWithThatNameAlreadyExists;
import com.example.art_plan_project_2.service.AnimalService;
import com.example.art_plan_project_2.validation.ResponseErrorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/animal")
public class AnimalController {
    private final AnimalService animalService;
    private final ResponseErrorValidation responseErrorValidation;

    @PostMapping("/create")
    public ResponseEntity<Object> createAnimal(@Valid @RequestBody AnimalDTO animalDTO,
                                               BindingResult bindingResult,
                                               Authentication authentication) {
        try {
            ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
            if (!ObjectUtils.isEmpty(errors)) return errors;
            User user = (User) authentication.getPrincipal();
            animalService.createAnimal(animalDTO, user);
            return new ResponseEntity<>("Animal created successfully", HttpStatus.OK);
        } catch (AnimalWithThatNameAlreadyExists e) {
            return new ResponseEntity<>("An animal with that name already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/edit/{animalId}")
    public ResponseEntity<Object> updateAnimal(@PathVariable(value = "animalId") Long id,
                                               @Valid @RequestBody AnimalDTO animalDTO,
                                               BindingResult bindingResult,
                                               Authentication authentication) {
        try {
            ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
            if (!ObjectUtils.isEmpty(errors)) return errors;
            User user = (User) authentication.getPrincipal();
            return new ResponseEntity<>(animalService.updateAnimal(id, animalDTO, user), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Didn't find this animal", HttpStatus.BAD_REQUEST);
        } catch (AnimalWithThatNameAlreadyExists e) {
            return new ResponseEntity<>("An animal with that name already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{animalId}")
    public ResponseEntity<Object> deleteAnimal(@PathVariable(value = "animalId") Long animalId,
                                               Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            animalService.deleteAnimal(animalId, user);
            return new ResponseEntity<>("Animal was deleted", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Didn't find this animal", HttpStatus.BAD_REQUEST);
        }
    }
}
