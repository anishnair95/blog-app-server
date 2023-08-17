package com.springboot.blog.service;

import com.springboot.blog.dto.CommentDto;

import java.util.List;

public interface CommentService {

    /**
     * Create a new comment for the post
     * @param postId id of the post
     * @param commentDto comment request object payload
     * @return Comment object with id
     */
    CommentDto createComment(Long postId, CommentDto commentDto);

    /**
     * Get all comments for a post
     * @param postId id of the post
     * @return List of comment object
     */
    List<CommentDto> getCommentsByPostId(Long postId);

    /**
     * Get a comment based on postId and commentId
     * @param postId id of the post
     * @param commentId id of the coomment
     * @return Comment object
     */
    CommentDto getCommentById(Long postId, Long commentId);

}
