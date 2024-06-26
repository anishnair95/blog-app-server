package com.springboot.blog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.blog.dto.CategoryDto;
import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Util class contain various convertors
 */
public class DataConvertor {

    private static final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private static final ObjectMapper mapper1 = new ObjectMapper().registerModule(new JavaTimeModule());

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
                .comments(post.getComments() != null ? post.getComments().stream().map(DataConvertor::commentEntityToDto).collect(Collectors.toSet())
                        : Collections.emptySet())
                .categoryId(post.getCategory() != null ? post.getCategory().getId() : null)
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

    public static Category categoryDtoToEntity(CategoryDto categoryDto) {
        return Category.builder()
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();
    }

    public static List<CategoryDto> categoryEntitiesToDto(List<Category> categories) {
        return categories.stream().map(DataConvertor::categoryEntityToDto).collect(Collectors.toList());
    }
    public static CategoryDto categoryEntityToDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    public static Category updateCategoryEntity(Category category, CategoryDto categoryDto) {

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        return category;
    }

    /**
     * Method to serialize object to json string
     * @param jsonString json string to be converted
     * @param clazz class type of the object
     * @return object of type T
     * @param <T> type of object
     *
     * this method throws exception if the jsonstring contains invalid parameter
     */
    public static <T> T deserializeJsonString(String jsonString, Class<T> clazz) {

        LOGGER.info("Inside deserialize convertor");
        if (jsonString != null && !jsonString.isEmpty()) {
            try {
                return mapper1.readValue(jsonString, clazz);
            } catch (Exception e) {
                LOGGER.error("Error in deserialization with error message: {}", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    /**
     * Method to serialize object to json string
     * @param json object to be converted
     * @param clazz class type of the object
     * @return object of type T
     * @param <T>
     *
     * This method will ignore the json string if it contains unknown properties
     */
    public static <T> T readValue(String json, Class<T> clazz) {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            String message = "Error while deserializing JSON object of type " + clazz.getCanonicalName();
            LOGGER.error(message, e);
            throw new RuntimeException(e);
        }
    }

}