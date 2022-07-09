package com.example.art_plan_project_2.response;

import lombok.Getter;

@Getter
public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid username";
        this.password = "Invalid password";
    }
}
