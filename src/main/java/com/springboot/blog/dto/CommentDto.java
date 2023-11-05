package com.springboot.blog.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "CommentDto Model Information")
public class CommentDto {

    private Long id;

    @Schema(description = "Blog Comment name")
    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Schema(description = "Blog Comment email")
    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @Schema(description = "Blog Comment body")
    @NotEmpty
    @Size(min = 10, message = "Comment should have atleast 10 characters")
    private String body;
//    private PostDto post;
}
