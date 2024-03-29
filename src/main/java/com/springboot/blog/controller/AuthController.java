package com.springboot.blog.controller;

import com.springboot.blog.dto.JwtAuthResponse;
import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;
import com.springboot.blog.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "REST APIs for Authentication Resource")
@RestController
@RequestMapping("/api/v1/auth")
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
     * @return JwtAuthResponse which contains token details after successful authentication
     */
    @Operation(summary = "Login API", description = "Login REST API to authenticate user and generate auth token")
    @ApiResponse(responseCode = "200", description = "HttpStatus success")
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        LOGGER.info("Inside AuthController.class login()");
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    /**
     * API to register a new user
     * @param registerDto request payload containing user details
     * @return message after the register process
     */
    @Operation(summary = "Register API", description = "Register REST API to register a new user")
    @ApiResponse(responseCode = "200", description = "HttpStatus success")
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> signup(@Valid @RequestBody RegisterDto registerDto) {

        LOGGER.info("Inside AuthController.class signup()");
        return new ResponseEntity<>(authService.register(registerDto), HttpStatus.CREATED);
    }
}
