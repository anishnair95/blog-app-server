package com.springboot.blog.controller;

import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;


public class BaseController {


//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<Map<String, Object>> runTimeExceptionHandler(RuntimeException ex) {
//
//        Map<String, Object> response = buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR.value());
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Map<String, Object>> exceptionHandler(ResourceNotFoundException ex) {

        Map<String, Object> response = buildErrorResponse(ex, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BlogApiException.class})
    public ResponseEntity<Map<String, Object>> exceptionHandler(BlogApiException ex) {

        Map<String, Object> response = buildErrorResponse(ex, ex.getStatus().value());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    private Map<String, Object> buildErrorResponse(RuntimeException ex, int statusCode) {
        Map<String,Object>response = new HashMap<>();
        response.put("success", false);
        Map<String, Object> reasons = new HashMap<String, Object>(){{
            put("code", statusCode);
            put("message", ex.getMessage());
        }};
        response.put("reasons", reasons);
        return response;
    }

}