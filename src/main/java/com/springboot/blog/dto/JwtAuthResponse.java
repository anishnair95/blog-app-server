package com.springboot.blog.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Builder design pattern
 */
@JsonDeserialize(builder = JwtAuthResponse.JwtAuthResponseBuilder.class)
public class JwtAuthResponse {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;

    public JwtAuthResponse(String accessToken, String tokenType, Long expiresIn) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public static JwtAuthResponseBuilder builder() {
        return new JwtAuthResponseBuilder();
    }
    public static final class JwtAuthResponseBuilder {
        private String accessToken;
        private String tokenType = "Bearer";
        private Long expiresIn;

        public JwtAuthResponseBuilder() {}

        public JwtAuthResponseBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public JwtAuthResponseBuilder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public JwtAuthResponseBuilder expiresIn(Long expiresIn) {
            this.expiresIn = expiresIn;
            return this;
        }

        public JwtAuthResponse build() {
            return new JwtAuthResponse(accessToken, tokenType, expiresIn);
        }
    }
}
