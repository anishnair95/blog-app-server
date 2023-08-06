package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import com.springboot.blog.util.DataConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);


    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    @Override
    public List<PostDto> getPosts(int pageNo, int pageSize) {
        LOGGER.info("Inside PostServiceImpl.class getPosts()");

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepository.findAll(pageable);
        return DataConvertor.postEntitiesToDto(posts.getContent());
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        LOGGER.info("Inside PostServiceImpl.class getPosts()");

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        List<PostDto> content = DataConvertor.postEntitiesToDto(posts.getContent());
        return PostResponse.builder()
                .content(content)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        LOGGER.info("Inside PostServiceImpl.class createPost()");
        Post savedPost = postRepository.save(DataConvertor.postDtoToEntity(postDto));
        return DataConvertor.postEntityToDto(savedPost);
    }

    @Override
    public PostDto getPostById(Long id) {
        LOGGER.info("Inside PostServiceImpl.class getPostById()");
        Optional<Post> post = postRepository.findById(id);
        return post
                .map(DataConvertor::postEntityToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Post.class.getSimpleName(),"id",id.toString()));
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        LOGGER.info("Inside PostServiceImpl.class updatePost()");

        //get post by id from database
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Post.class.getSimpleName(), "id", id.toString()));


        //update postDto to post object and save in DB
        return DataConvertor.postEntityToDto(postRepository.save(DataConvertor.updatePostEntity(post, postDto)));

    }

    @Override
    public void deletePost(Long id) {
        LOGGER.info("Inside PostServiceImpl.class deletePost()");
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Post.class.getSimpleName(), "id", id.toString()));

        postRepository.delete(post);
    }
}
