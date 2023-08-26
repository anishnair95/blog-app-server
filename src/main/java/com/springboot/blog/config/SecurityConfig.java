package com.springboot.blog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableMethodSecurity // enables method level security
public class SecurityConfig {

    /**
     * Configuration
     * <br/>
     * The configuration would be like create, post, update and delete will be accessible by only ADMIN users.
     * <br/>
     * GET APIs will be accessible by users
     * @return
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    //  SecurityFilterChain - it's an interface
    // DefaultSecurityFilterChain - it's an implementation class

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // we have to authorize any requests so we have use method anyRequest()
        httpSecurity.csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        // enable only when it is required to authenticate all requests
                        // authorize.anyRequest().authenticated())
                        // allow anyone to access the url pattern without auth
                        //  authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        authorize.requestMatchers(HttpMethod.GET, "/api/**").hasAnyRole("USER","ADMIN")
                                .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        //the above configuration sets a basic authentication
        return httpSecurity.build();
    }

    //in-memory authentication
    @Bean
    public UserDetailsService getUserDetails() {

        UserDetails stdUser = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("password"))
                .roles("ADMIN")
                .build();

        //storing the user in-memory
        return new InMemoryUserDetailsManager(stdUser, adminUser);
    }
}
