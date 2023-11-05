package com.springboot.blog.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "PostDto Model Information")
public class PostDto {

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
}
