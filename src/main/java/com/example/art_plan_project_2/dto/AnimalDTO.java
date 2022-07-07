package com.example.art_plan_project_2.dto;

import com.example.art_plan_project_2.entity.Animal;
import com.example.art_plan_project_2.entity.User;
import com.example.art_plan_project_2.enums.Gender;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalDTO {

    private Long id;

    @NotNull
    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    @Size(min = 2, max = 128, message = "Login should be between 2 and 128 characters")
    private String name;


    public static AnimalDTO from(Animal animal) {
        return AnimalDTO.builder()
                .id(animal.getId())
                .dateOfBirth(animal.getDateOfBirth())
                .gender(animal.getGender())
                .name(animal.getName())
                .build();
    }
}
