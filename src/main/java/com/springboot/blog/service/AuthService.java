package com.springboot.blog.service;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;

public interface AuthService {

    /**
     * login method to authenticate the user with the provided details
     * @param loginDto payload containing user details
     * @return response message after successful login request
     */
    String login(LoginDto loginDto);

    /**
     * create and registers a new user.
     * @param registerDto payload containing user details
     * @return response message after successful registeration
     */
    String register(RegisterDto registerDto);
}
