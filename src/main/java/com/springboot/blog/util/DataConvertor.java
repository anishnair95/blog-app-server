package com.springboot.blog.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Util class contain various convertors
 */
public class DataConvertor {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
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
                .description(post.getDescription())
                .comments(post.getComments().stream().map(DataConvertor::commentEntityToDto).collect(Collectors.toSet()))
                .build();
    }

    public static List<PostDto> postEntitiesToDto(List<Post> posts) {
        return posts.stream().map(DataConvertor::postEntityToDto).collect(Collectors.toList());
    }


    //using mapper
    public static PostDto postEntityToDto(Post post, ModelMapper modelMapper) {
        return modelMapper.map(post, PostDto.class);
    }

    //using mapper
    public static Post postDtoToEntity(PostDto postDto, ModelMapper modelMapper) {
        return modelMapper.map(postDto, Post.class);
    }

    //using mapper
    public static List<PostDto> postEntitiesToDto(List<Post> posts, ModelMapper modelMapper) {
        return posts.stream().map( p -> DataConvertor.postEntityToDto(p, modelMapper)).collect(Collectors.toList());
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

    public static List<CommentDto> commentEntitiesToDto(Set<Comment> comments) {
        return comments.stream().map(DataConvertor::commentEntityToDto).collect(Collectors.toList());
    }

    //using mapper
    public static Comment commentDtoToEntity(CommentDto commentDto, ModelMapper modelMapper) {
        return modelMapper.map(commentDto, Comment.class);
    }

    //using mapper
    public static CommentDto commentEntityToDto(Comment comment, ModelMapper modelMapper) {
        return modelMapper.map(comment, CommentDto.class);
    }

    //using mapper
    public static List<CommentDto> commentEntitiesToDto(List<Comment> comments, ModelMapper modelMapper) {
        return comments.stream().map( c -> DataConvertor.commentEntityToDto(c, modelMapper)).collect(Collectors.toList());
    }

    public static Comment updateCommentEntity(Comment comment, CommentDto commentDto) {
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }

    public static <T> T deserializeJsonString(String jsonString, Class<T> clazz) {

        LOGGER.info("Inside deserialize convertor");
        if (jsonString != null && !jsonString.isEmpty()) {
            try {
                return mapper.readValue(jsonString, clazz);
            } catch (Exception e) {
                LOGGER.error("Error in deserialization with error message: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return null;
    }

}