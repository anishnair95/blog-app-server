package com.springboot.blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterDto {

    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank(message = "Email should not be null or empty")
    @Email
    private String email;

    @NotBlank
    private String password;
}
