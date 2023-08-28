package com.springboot.blog.repository;

import com.springboot.blog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository layer for User entity
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    //JPQL with named query parameters
    @Query(value = "select u from User u where u.username = :user or u.email = :email")
    Optional<User> findUserByUsernameOrEmail(@Param(value = "user") String username, @Param(value = "email") String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username); //just checks if user exists by username or not

    @Query(value = "select CASE "
            + "WHEN count(u) >0 THEN true"
            + "ELSE false END from User u where u.username = :username")
    Boolean findUserExistsByUsername(String username);

    Boolean existsByEmail(String email);
}
