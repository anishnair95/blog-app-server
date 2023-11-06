package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "LoginDto Model Information")
public class LoginDto {

    @NotBlank
    @Schema(description = "LoginDto usernameOrEmail")
    private String usernameOrEmail;

    @NotBlank
    @Schema(description = "LoginDto password")
    private String password;
}