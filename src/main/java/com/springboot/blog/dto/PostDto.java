package com.springboot.blog.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {

    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should have atleast 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have atleast 10 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments;
}
