package com.example.art_plan_project_2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class AnimalWithThatNameAlreadyExists extends RuntimeException {

}
