package com.example.art_plan_project_2.controller;

import com.example.art_plan_project_2.dto.UserDTO;
import com.example.art_plan_project_2.exception.UserAlreadyRegisteredException;
import com.example.art_plan_project_2.response.JWTTokenSuccessResponse;
import com.example.art_plan_project_2.security.JWTTokenProvider;
import com.example.art_plan_project_2.security.SecurityConstants;
import com.example.art_plan_project_2.service.UserService;
import com.example.art_plan_project_2.validation.ResponseErrorValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final ResponseErrorValidation responseErrorValidation;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody UserDTO userDTO,
                                                   BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userDTO.getLogin(),
                userDTO.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDTO userDTO,
                                               BindingResult bindingResult) {
        try {
            ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
            if (!ObjectUtils.isEmpty(errors)) return errors;
            userService.createUser(userDTO);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (UserAlreadyRegisteredException e) {
            return new ResponseEntity<>("This login already exists", HttpStatus.BAD_REQUEST);
        }
    }
}
