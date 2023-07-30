package com.springboot.blog.service;

import com.springboot.blog.dto.PostDto;

import java.util.List;

public interface PostService {

    /**
     * Get All posts details
     * @return list of Post object
     */
    List<PostDto> getAllPosts();

    /**
     * Create a new post
     * @param postDto Request object containing new post data
     * @return returns Post object after successful creation or null.
     */
    PostDto createPost(PostDto postDto);


    /**
     * Get Post by id
     * @param id id of the post to get
     * @return Post object for the given id
     */
    PostDto getPostById(Long id);


    /**
     * Update Post by id
     * @param postDto post object with updated details
     * @param id id of the post object to update
     * @return updated post object
     */
    PostDto updatePost(PostDto postDto, Long id);

    /**
     * Delete post by id
     * @param id id of post object to remove
     */
    void deletePost(Long id);

}
