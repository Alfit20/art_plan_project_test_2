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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
