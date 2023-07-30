package com.springboot.blog.controller;


import com.springboot.blog.dto.PostDto;
import com.springboot.blog.service.PostService;
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

import javax.xml.ws.Response;

@RestController
@RequestMapping("/api/posts")
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
    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
        LOGGER.info("Inside PostController.class createPost");
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    /**
     * Get all posts
     * @return List of posts
     */
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        LOGGER.info("Inside PostController.class getPosts");
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    /**
     * Get post details by id
     * @param id id of the required post
     * @return Post object with details
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") Long id) {
        LOGGER.info("Inside PostController.class getPostById");
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @GetMapping("/health")
    public String getStatus() {
        LOGGER.info("Inside getStatus");
        return "OK";
    }


    /**
     * Update post by id
     * @param postDto  object payload with post details
     * @param id id of the post object to update
     * @return Post object
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable(name="id") Long id) {
        LOGGER.info("Inside PostController.class updatePost");
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    /**
     * Delete post by id
     * @param id id of the post to delete
     * @return returns success message if post deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        LOGGER.info("Inside PostController.class deletePost");
        postService.deletePost(id);

        return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
    }


}
