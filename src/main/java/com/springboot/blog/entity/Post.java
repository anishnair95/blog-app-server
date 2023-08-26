package com.springboot.blog.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
//specifying unique constraints in our table
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
@Builder
@Getter
@Setter
@NoArgsConstructor // since hibernate uses proxies to create objects so we need noargconstr
@AllArgsConstructor

/**
 * Entity class for Post
 */
public class Post {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    //We are specifying CascadeType.ALL because whenever an operation is performed on parent the child operation will also be taken care - insert, update, delete
    //orphanRemoval = true - because we want to delete child object as well.
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Comment> comments = new HashSet<>();
}
