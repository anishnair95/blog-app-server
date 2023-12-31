package com.springboot.blog.util;

import com.springboot.blog.entity.Post;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class MockDataGenerator {

    public static List<Post> getMockPosts() {
        Post post = Post.builder().id(1234L)
                .title("test title")
                .content("test content")
                .description("test")
                .category(null)
                .comments(null)
                .build();
        return Arrays.asList(post);
    }

    public static Page<Post> getMockPostPage(List<Post> posts) {
       return new Page<Post>() {
            @Override
            public int getTotalPages() {
                return 1;
            }

            @Override
            public long getTotalElements() {
                return 1;
            }

            @Override
            public <U> Page<U> map(Function<? super Post, ? extends U> converter) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 10;
            }

            @Override
            public int getNumberOfElements() {
                return 1;
            }

            @Override
            public List<Post> getContent() {
                return posts;
            }

            @Override
            public boolean hasContent() {
                return true;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @NotNull
            @Override
            public Iterator<Post> iterator() {
                return null;
            }
        };
    }
}
