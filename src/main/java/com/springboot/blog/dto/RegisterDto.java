package com.springboot.blog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "RegisterDto Model Information")
public class RegisterDto {

    @Schema(description = "Blog RegisterDto name")
    @NotBlank
    private String name;

    @Schema(description = "Blog RegisterDto username")
    @NotBlank
    private String username;

    @Schema(description = "Blog RegisterDto email")
    @NotBlank(message = "Email should not be null or empty")
    @Email
    private String email;

    @Schema(description = "Blog RegisterDto password")
    @NotBlank
    private String password;
}
