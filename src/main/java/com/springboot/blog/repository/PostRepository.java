package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository layer for Post entity
 */
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByCategoryId(Long id);

    // TODO modify query it won't work
    @Query(value = "select * from posts where :column :comparator :column_value "
            + "OR (:column = :column_value AND id > :id) "
            + "order by :column :dir, id ASC LIMIT :pageSize", nativeQuery = true)
    List<Post> findAllPostsByCursor(@Param("column") String column, @Param("column_value") String column_value, @Param("comparator") String comparator,
            @Param("id") String id, @Param("dir") String dir, @Param("pageSize") int pageSize);

    @Query(value = "SELECT * FROM posts WHERE id :comparator :id ORDER BY id :dir LIMIT :pageSize", nativeQuery = true)
    List<Post> findPostsByIdCursor(@Param("comparator") String comparator, @Param("id") String id, @Param("dir") String dir,
            @Param("pageSize") int pageSize);
}
