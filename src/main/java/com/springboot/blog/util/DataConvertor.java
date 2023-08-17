package com.springboot.blog.util;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Util class contain various convertors
 */
public class DataConvertor {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataConvertor.class);

    public static Post postDtoToEntity(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .description(postDto.getDescription())
                .content(postDto.getContent()).build();
    }

    public static PostDto postEntityToDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .description(post.getDescription()).build();
    }

    public static List<PostDto> postEntitiesToDto(List<Post> posts) {
        return posts.stream().map(DataConvertor::postEntityToDto).collect(Collectors.toList());
    }


    public static Post updatePostEntity(Post post, PostDto postDto) {
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());

        return post;
    }

    public static Comment commentDtoToEntity(CommentDto commentDto) {
        return Comment.builder()
                .name(commentDto.getName())
                .email(commentDto.getEmail())
                .body(commentDto.getBody())
//                .post(commentDto.getPost() != null ? postDtoToEntity(commentDto.getPost()) : null)
                .build();
    }

    public static CommentDto commentEntityToDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .name(comment.getName())
                .email(comment.getEmail())
                .body(comment.getBody())
//                .post(comment.getPost() != null ? postEntityToDto(comment.getPost()) : null)
                .build();
    }

    public static List<CommentDto> commentEntitiesToDto(List<Comment> comments) {
        return comments.stream().map(DataConvertor::commentEntityToDto).collect(Collectors.toList());
    }
}