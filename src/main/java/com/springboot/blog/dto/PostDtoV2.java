package com.springboot.blog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDtoV2 {

    private Long id;

    @Schema(description = "Blog PostDto title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have atleast 2 characters")
    private String title;

    @Schema(description = "Blog PostDto description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have atleast 10 characters")
    private String description;

    @Schema(description = "Blog PostDto content")
    @NotEmpty
    private String content;

    @Schema(description = "Blog PostDto comments")
    private Set<CommentDto> comments;

    @Schema(description = "Blog PostDto categoryId")
    private Long categoryId;

    private List<String> tags = new ArrayList<>();
}
