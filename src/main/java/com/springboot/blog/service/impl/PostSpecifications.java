package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Post;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

// TODO: use this as well
public class PostSpecifications {

    public static Specification<Post> idCursorSpec(String columnName, String comparator, Long value) {
        return (Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            switch (comparator) {
                case ">":
                    return criteriaBuilder.greaterThan(root.get(columnName), value);
                case "<":
                    return criteriaBuilder.lessThan(root.get(columnName), value);
                // Add more cases for other comparators as needed
                default:
                    return null;
            }
        };
    }
}