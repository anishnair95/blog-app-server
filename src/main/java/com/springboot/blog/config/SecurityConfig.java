package com.springboot.blog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    //  SecurityFilterChain - it's an interface
    // DefaultSecurityFilterChain - it's an implementation class
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // we have to authorize any requests so we have use method anyRequest()
        httpSecurity.csrf().disable()
                .authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        //the above configuration sets a basic authentication
        return httpSecurity.build();
    }
}
