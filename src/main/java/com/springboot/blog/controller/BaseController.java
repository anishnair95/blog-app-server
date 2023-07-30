package com.springboot.blog.controller;

import com.springboot.blog.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


public class BaseController {


    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> exceptionHandler(ResourceNotFoundException ex) {

        Map<String, Object> response = buildErrorResponse(ex);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private Map<String, Object> buildErrorResponse(ResourceNotFoundException ex) {
        Map<String,Object>response = new HashMap<>();
        response.put("success", false);
        Map<String, Object> reasons = new HashMap<String, Object>(){{
            put("code", HttpStatus.NOT_FOUND.value());
            put("message", ex.getMessage());
        }};
        response.put("reasons", reasons);
        return response;
    }

}