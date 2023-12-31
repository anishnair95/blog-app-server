package com.springboot.blog.controller;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import jakarta.validation.Valid;

@Tag(name = "CRUD REST APIs for Comment Resource")
@RestController
@RequestMapping("/api/")
public class CommentController extends BaseController {

    private final CommentService commentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //@RequestBody - it denotes that the specified object should contain the request payload data
    // spring will automatically convert json to java object using HttpMessageConvertor


    /**
     * Create comment API
     * @param postId id of the post
     * @param commentDto comment payload with details
     * @return CommentDto response object
     */
    @Operation(summary = "Create comment REST API", description = "Create comment REST API to create comment by post id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "HttpStatus 201 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable(name = "postId") Long postId, @Valid @RequestBody CommentDto commentDto) {
        LOGGER.info("Inside CommentController.class createComment");
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    /**
     * Get all comments
     * @param postId id of the post
     * @return List of comments
     */
    @Operation(summary = "Get comments by post REST API", description = "Get comments REST API to get comments by post id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(name = "postId") Long postId) {
        LOGGER.info("Inside CommentController.class getCommentsByPostId");
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

    /**
     * Get a comment based on postId and commentId
     * @param postId id of the post
     * @param commentId id of the comment
     * @return Comment object
     */
    @Operation(summary = "Get comment REST API", description = "Get comment REST API to get comment by post id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId) {
        LOGGER.info("Inside CommentController.class getCommentById");
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    /**
     * Update comment details
     *
     * @param postId id of the post
     * @param commentId id of the comment
     * @param commentDto Comment payload object
     * @return updated comment object
     */
    @Operation(summary = "Update comment REST API", description = "Update comment REST API to update comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId,
            @Valid @RequestBody CommentDto commentDto) {
        LOGGER.info("Inside CommentController.class updateComment");
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    /**
     * Delete comment API
     *
     * @param postId id of the post
     * @param commentId id of the comment
     * @return success message
     */
    @Operation(summary = "Delete comment by id REST API", description = "Delete comment REST API to delete comment by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "postId") Long postId,
            @PathVariable(name = "commentId") Long commentId) {
        LOGGER.info("Inside CommentController.class deleteComment");
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }

}
