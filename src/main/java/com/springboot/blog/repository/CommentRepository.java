package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository layer for Comment Entity
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long postId);

    //traverse associated derived query
    List<Comment> findAllByPost_Title(String title);


    //find the comment by post.id and commentid
    Optional<Comment> findCommentByPost_IdAndId(Long postId, Long commentId);
}
