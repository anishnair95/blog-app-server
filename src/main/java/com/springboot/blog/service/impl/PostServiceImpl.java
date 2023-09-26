package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import com.springboot.blog.util.DataConvertor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);


    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Deprecated
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

//        List<PostDto> content = DataConvertor.postEntitiesToDto(posts.getContent(), modelMapper);
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
//        Post savedPost = postRepository.save(DataConvertor.postDtoToEntity(postDto, modelMapper));
        Category category = getCategory(postDto);
        Post post = DataConvertor.postDtoToEntity(postDto);
        post.setCategory(category);
        Post savedPost = postRepository.save(post);
        return DataConvertor.postEntityToDto(savedPost);
    }

    private Category getCategory(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(Category.class.getSimpleName(), "id", postDto.getCategoryId().toString()));
        return category;
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

        Category category = getCategory(postDto);
        //get post by id from database
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Post.class.getSimpleName(), "id", id.toString()));


        Post updatedPost = DataConvertor.updatePostEntity(post, postDto);
        updatedPost.setCategory(category);
        //update postDto to post object and save in DB
        return DataConvertor.postEntityToDto(postRepository.save(updatedPost));
    }

    @Override
    public void deletePost(Long id) {
        LOGGER.info("Inside PostServiceImpl.class deletePost()");
        Post post = postRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(Post.class.getSimpleName(), "id", id.toString()));

        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        LOGGER.info("Inside PostServiceImpl.class getPostsByCategory()");
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class.getSimpleName(), "id", categoryId.toString()));
        return DataConvertor.postEntitiesToDto(postRepository.findByCategoryId(categoryId));
    }
}
