package com.springboot.blog.controller;

import com.springboot.blog.dto.ErrorDetails;
import com.springboot.blog.dto.ErrorResponse;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class BaseController {


//    @ExceptionHandler(value = RuntimeException.class)
//    public ResponseEntity<Map<String, Object>> runTimeExceptionHandler(RuntimeException ex) {
//
//        Map<String, Object> response = buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR.value());
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(value = {ResourceNotFoundException.class})
//    public ResponseEntity<Map<String, Object>> exceptionHandler(ResourceNotFoundException ex) {
//
//        Map<String, Object> response = buildErrorResponse(ex, HttpStatus.NOT_FOUND.value());
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<ErrorResponse> exceptionHandler(ResourceNotFoundException ex, WebRequest webRequest) {

        ErrorResponse response = buildErrorResponse(ex, HttpStatus.NOT_FOUND.value(),
                webRequest.getDescription(false)); //false so that will not include other extra info
                                                                 // other than url
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {BlogApiException.class})
    public ResponseEntity<Map<String, Object>> exceptionHandler(BlogApiException ex) {

        Map<String, Object> response = buildErrorResponse(ex, ex.getStatus().value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> baseExceptionHandler(Exception ex, WebRequest webRequest) {
        return new ResponseEntity<>(buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR.value(), webRequest.getDescription(false)),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //  global exception handler
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

    private ErrorResponse buildErrorResponse(Exception ex, int statusCode, String details) {
        ErrorDetails reasons = new ErrorDetails(statusCode, new Date(), ex.getMessage(), details);
        return new ErrorResponse(false, reasons);
    }

}