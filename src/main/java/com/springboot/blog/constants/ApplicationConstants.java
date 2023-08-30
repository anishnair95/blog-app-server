package com.springboot.blog.constants;

import java.util.List;

/**
 * interface to manage constants
 * Since all variables in interface are public, static and final by default
 */
public interface ApplicationConstants {

    String LOGIN_MESSAGE = "User Logged-in successfully !!";
    List<String> REQUIRED_BOOLEAN_VALUES = List.of("TRUE", "FALSE");

}
