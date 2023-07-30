package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository layer for Post entity
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
