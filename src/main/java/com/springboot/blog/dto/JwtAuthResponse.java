package com.springboot.blog.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Builder design pattern
 */
@JsonDeserialize(builder = JwtAuthResponse.JwtAuthResponseBuilder.class)
public class JwtAuthResponse {

    private String accessToken;
    private String tokenType;

    public JwtAuthResponse(String accessToken, String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
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

    public static JwtAuthResponseBuilder builder() {
        return new JwtAuthResponseBuilder();
    }
    public static final class JwtAuthResponseBuilder {
        private String accessToken;
        private String tokenType = "Bearer";

        public JwtAuthResponseBuilder() {}

        public JwtAuthResponseBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public JwtAuthResponseBuilder tokenType(String tokenType) {
            this.tokenType = tokenType;
            return this;
        }

        public JwtAuthResponse build() {
            return new JwtAuthResponse(accessToken, tokenType);
        }
    }
}
