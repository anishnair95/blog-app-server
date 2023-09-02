package com.springboot.blog.constants;

import java.util.List;

/**
 * interface to manage constants
 * Since all variables in interface are public, static and final by default
 */
public interface ApplicationConstants {

    String LOGIN_MESSAGE = "User Logged-in successfully !!";
    List<String> REQUIRED_BOOLEAN_VALUES = List.of("TRUE", "FALSE");
    String REGISTER_MESSAGE = "User registered successfully !!";
    String ROLE_USER = "ROLE_USER";
    String ROLE_ADMIN = "ROLE_ADMIN";

}
