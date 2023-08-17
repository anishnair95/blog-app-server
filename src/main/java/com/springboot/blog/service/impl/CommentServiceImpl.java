package com.springboot.blog.service.impl;

import com.springboot.blog.dto.CommentDto;
import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.util.DataConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        LOGGER.info("Inside CommentServiceImpl.class createComment()");
        //retrieve post by id;
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(Post.class.getSimpleName(), "id", postId.toString()));
        Comment comment = DataConvertor.commentDtoToEntity(commentDto);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        return DataConvertor.commentEntityToDto(savedComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        LOGGER.info("Inside CommentServiceImpl.class getCommentsByPostId()");
        //retrieve post by id
        return DataConvertor.commentEntitiesToDto(commentRepository.findByPostId(postId));
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        LOGGER.info("Inside CommentServiceImpl.class getCommentById()");
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException(Post.class.getSimpleName(), "id", String.valueOf(postId)));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException(Comment.class.getSimpleName(), "id", String.valueOf(commentId)));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return DataConvertor.commentEntityToDto(comment);
    }

    private void justCheck() {
        String title = "My New Post";

        List<Comment> comments = commentRepository.findAllByPost_Title(title);

        int size = comments != null ? comments.size() : 0;
        System.out.println("SIze = " + size);
    }
}
