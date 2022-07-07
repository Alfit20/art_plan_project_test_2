package com.example.art_plan_project_2.dto;

import com.example.art_plan_project_2.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @NotBlank
    @Size(min = 3, max = 128, message = "Login should be between 3 and 128 characters")
    private String login;

    @NotBlank
    @Size(min = 3, max = 128, message = "Password should be between 3 and 128 characters")
    private String password;

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .build();
    }
}
