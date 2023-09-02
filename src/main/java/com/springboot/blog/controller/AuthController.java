package com.springboot.blog.controller;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;
import com.springboot.blog.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Login API to authenticate the user
     * @param loginDto request payload containing user details
     * @return message after the login process
     */
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {

        LOGGER.info("Inside AuthController.class login()");
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    /**
     * API to register a new user
     * @param registerDto request payload containing user details
     * @return message after the register process
     */
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterDto registerDto) {

        LOGGER.info("Inside AuthController.class signup()");
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }
}
