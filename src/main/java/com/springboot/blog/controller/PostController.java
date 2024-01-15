package com.springboot.blog.controller;


import static com.springboot.blog.util.ApplicationConstants.DEFAULT_CURSOR;
import static com.springboot.blog.util.ApplicationConstants.DEFAULT_PAGE_NUMBER;
import static com.springboot.blog.util.ApplicationConstants.DEFAULT_PAGE_SIZE;
import static com.springboot.blog.util.ApplicationConstants.DEFAULT_SORT_BY;
import static com.springboot.blog.util.ApplicationConstants.DEFAULT_SORT_DIRECTION;

import com.springboot.blog.dto.PostCursorResponse;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.dto.PostsCursor;
import com.springboot.blog.service.PostService;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/posts")
@Tag(name = "CRUD REST APIs for POST resource")
public class PostController extends BaseController {

    private final PostService postService;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class);

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    //@RequestBody - it denotes that the specified object should contain the request payload data
    // spring will automatically convert json to java object using HttpMessageConvertor

    /**
     * Create post API
     * @param postDto Request object
     * @return PostDto response
     */

    @Operation(summary = "Create Post REST API", description = "Create Post REST API to create new posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "HttpStatus 201 created"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    // note: while mentioning role by default prefix 'ROLE' is checked
    // if the prefix is not present then it is automatically added by internal functions but this is not same when defining role check in SecurityConfig
    @SecurityRequirement(name = "Bear Authentication") // swagger related - this API needs token
    @PreAuthorize("hasRole('ADMIN')") // only accessible by ADMIN
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto) {
        LOGGER.info("Inside PostController.class createPost");
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    /**
     * Get all posts
     * @param pageNo page number to get the list of posts
     * @param pageSize number of posts required in single response
     * @return List of posts
     */
    @Operation(summary = "Get Posts REST API", description = "Get posts REST API to get all the posts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(name = "pageNo", defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        LOGGER.info("Inside PostController.class getPosts");
        LOGGER.info("Request params: pageNo:{}, pageSize: {}, sortBy: {}, sortDir: {}", pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    /**
     * Get all posts v2
     * @param pageSize number of posts required in single response
     * @param sortBy column based on which records to be sorted
     * @param sortDir sort direction (ASC, DESC)
     * @param cursor cursor value to fetch next set of results
     * @return posts results
     */
    @Operation(summary = "Get Posts REST API", description = "Get posts REST API to get all the posts by cursor pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/cursor")
    public ResponseEntity<PostCursorResponse> getAllPostsByCursor(
            @RequestParam(name = "pageSize", defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(name = "sortBy", defaultValue = DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(name = "cursor", defaultValue = DEFAULT_CURSOR, required = false) String cursor) {
        LOGGER.info("Inside PostController.class getAllPostsByCursor");
        LOGGER.info("Request params: pageSize: {}, sortBy: {}, sortDir: {}, cursor: {}", pageSize, sortBy, sortDir, cursor);
        PostsCursor postsCursor = postService.processCursor(cursor, sortBy, sortDir, pageSize);
        return new ResponseEntity<>(
                postService.getAllPostsCursorPagination(postsCursor.getPageId(), postsCursor.getPageKey(), sortBy, sortDir, postsCursor.getPageSize()),
                HttpStatus.OK);
    }

    /**
     * Get post details by id
     * @param id id of the required post
     * @return Post object with details
     */
    @Operation(summary = "Get Post REST API", description = "Get post REST API to get post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") Long id) {
        LOGGER.info("Inside PostController.class getPostById");
        return ResponseEntity.ok(postService.getPostById(id));
    }

    /**
     * Update post by id
     * @param postDto  object payload with post details
     * @param id id of the post object to update
     * @return Post object
     */
    @Operation(summary = "Update Post REST API", description = "Update post REST API to update post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody @Valid PostDto postDto, @PathVariable(name = "id") Long id) {
        LOGGER.info("Inside PostController.class updatePost");
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    /**
     * Delete post by id
     * @param id id of the post to delete
     * @return returns success message if post deleted successfully
     */
    @Operation(summary = "Delete Post REST API", description = "Delete post REST API to delete post by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        LOGGER.info("Inside PostController.class deletePost");
        postService.deletePost(id);
        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }

    /**
     * Get Posts based on category
     * @param categoryId id of the category
     * @return List of posts based on category
     */
    @Operation(summary = "Get post by category REST API", description = "Get post REST API to get post by category id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "HttpStatus 200 success"),
            @ApiResponse(responseCode = "500", description = "HttpStatus 500 internal server error")
    })
    @SecurityRequirement(name = "Bear Authentication")
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Long categoryId) {
        LOGGER.info("Inside PostController.class getPostsByCategory");
        return new ResponseEntity<>(postService.getPostsByCategory(categoryId), HttpStatus.OK);
    }
}
