package com.springboot.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.blog.entity.Post;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {

    private Long id;

    @NotEmpty(message = "name should not be null or empty")
    private String name;

    @NotEmpty(message = "description should not be null or empty")
    private String description;

    private Set<Post> posts;
}
