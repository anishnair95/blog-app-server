package com.springboot.blog.service.impl;

import static com.springboot.blog.constants.ApplicationConstants.LOGIN_MESSAGE;
import static com.springboot.blog.constants.ApplicationConstants.REGISTER_MESSAGE;
import static java.lang.String.format;

import com.springboot.blog.dto.LoginDto;
import com.springboot.blog.dto.RegisterDto;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
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
        LOGGER.info(LOGIN_MESSAGE);
        return jwtTokenProvider.generateToken(authentication);
//        return LOGIN_MESSAGE;
    }

    @Override
    public String register(RegisterDto registerDto) {
        LOGGER.info("Inside AuthServiceImpl.class register()");

        // check whether email already exists
        if (userRepository.findUserExistsByUsername(registerDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, format("Username '%s' already exists !!", registerDto.getUsername()));
        }
        // check whether email already exists
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, format("Email '%s' already exists !!", registerDto.getEmail()));
        }
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new ResourceNotFoundException(Role.class.getSimpleName(), "name", "ROLE_USER"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = User.builder()
                .name(registerDto.getName())
                .username(registerDto.getUsername())
                .email(registerDto.getEmail())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(user);
        return REGISTER_MESSAGE;
    }
}
