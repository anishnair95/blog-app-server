package com.springboot.blog.service;

import com.springboot.blog.dto.LoginDto;

public interface AuthService {

    /**
     * loging method to authenticate the user with the provided details
     * @param loginDto payload containing user details
     * @return response message after successful login request
     */
    String login(LoginDto loginDto);
}
