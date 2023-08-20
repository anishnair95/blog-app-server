package com.springboot.blog.dto;

public class ErrorResponse {

    private final boolean success;
    private final ErrorDetails reasons;

    public ErrorResponse(boolean success, ErrorDetails reasons) {
        this.success = success;
        this.reasons = reasons;
    }

    public boolean isSuccess() {
        return success;
    }

    public ErrorDetails getReasons() {
        return reasons;
    }
}
