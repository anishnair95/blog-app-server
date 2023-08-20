package com.springboot.blog.dto;

import java.util.Date;

/**
 * Error details class
 */
public class ErrorDetails {

    private final int code;
    private final Date timestamp;
    private final String message;
    private final String details;

    public ErrorDetails(int code, Date timestamp, String message, String details) {
        this.code = code;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public int getCode() {
        return code;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
