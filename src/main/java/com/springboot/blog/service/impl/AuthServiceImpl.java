package com.springboot.blog.service.impl;

import static com.springboot.blog.constants.ApplicationConstants.LOGIN_MESSAGE;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * login function which authenticates using the given username and password.
     * <br/>
     * After successfully authentication the object is stored in SecurityContextHolder
     * @param loginDto object containing user details
     * @return message after login
     */
    @Override
    public String login (LoginDto loginDto) {
        LOGGER.info("Inside AuthServiceImpl.class login()");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return LOGIN_MESSAGE;
    }

}
