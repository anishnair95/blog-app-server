package com.springboot.blog.controller;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlogApiConfig {

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }
}
