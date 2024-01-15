package com.springboot.blog.service;

import com.springboot.blog.dto.PostCursorResponse;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.dto.PostsCursor;

import java.util.List;

public interface PostService {

    /**
     * Get All posts details
     * @param pageNo page number to get the list of posts
     * @param pageSize number of posts in single response
     * @return list of Post object
     */
    @Deprecated
    List<PostDto> getPosts(int pageNo, int pageSize);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

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

    /**
     * Get Posts based on category id
     * @param categoryId id of the category
     * @return List of all posts based on category
     */
    List<PostDto> getPostsByCategory(Long categoryId);

    PostsCursor processCursor(String cursor, String sortBy, String sortDir, int pageSize);

    PostCursorResponse getAllPostsCursorPagination(String pageId, String pageKey, String sortBy, String sortDir, int pageSize);

}
