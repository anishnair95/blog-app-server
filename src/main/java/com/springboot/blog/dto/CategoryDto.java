package com.springboot.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.springboot.blog.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "CategoryDto Model Information")
public class CategoryDto {

    private Long id;

    @Schema( description =  "Blog Category name")
    @NotEmpty(message = "name should not be null or empty")
    private String name;

    @Schema( description =  "Blog Category description")
    @NotEmpty(message = "description should not be null or empty")
    private String description;

    @Schema( description =  "Blog Category Post")
    private Set<Post> posts;
}
