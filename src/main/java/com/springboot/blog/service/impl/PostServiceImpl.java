package com.springboot.blog.service.impl;

import com.springboot.blog.dto.PostCursorResponse;
import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.dto.PostsCursor;
import com.springboot.blog.entity.Category;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;
import com.springboot.blog.util.DataConvertor;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final CategoryRepository categoryRepository;

    private final ModelMapper modelMapper;

    private final EntityManager entityManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);


    @Autowired
    public PostServiceImpl(PostRepository postRepository, CategoryRepository categoryRepository, ModelMapper modelMapper, EntityManager entityManager) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
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
    public PostCursorResponse getAllPostsCursorPagination(String pageId, String pageKey, String sortBy, String sortDir, int pageSize) {
        LOGGER.info("Inside PostServiceImpl.class getAllPostsCursorPagination()");

        String comparator = ">";
        if (!sortDir.equalsIgnoreCase("ASC")) {
          comparator = "<";
        }

        List<Post> posts = Collections.emptyList();
        if (sortBy.equalsIgnoreCase("id")) {
            posts = findPostsByDynamicCriteria(comparator, pageId, sortDir, pageSize);
//            posts = postRepository.findPostsByIdCursor(comparator, pageId, sortDir, pageSize);

        } else {
            // TODO: modify use entitymanager or jpa specification
            posts = postRepository.findAllPostsByCursor(sortBy, pageKey, comparator, pageId, sortDir, pageSize);

        }

        // process the next cursor
        PostsCursor postsCursor = getNextPageCursor(sortBy, sortDir, pageSize, posts);
        return PostCursorResponse.builder()
                .data(DataConvertor.postEntitiesToDto(posts))
                .nextPageCursor(postsCursor != null ? postsCursor.encodeCursorString() : null)
                .build();
    }

    public List<Post> findPostsByDynamicCriteria(String comparator, String value, String dir, int limit) {
        String query = "SELECT p FROM Post p WHERE p.id " + comparator + " :value ORDER BY p.id " + dir;
        return entityManager.createQuery(query, Post.class)
                .setParameter("value", value)
                .setMaxResults(limit)
                .getResultList();
    }

    private PostsCursor getNextPageCursor(String sortBy, String sortDir, int pageSize, List<Post> posts) {

        if (posts.size() == 0) {
            return null;
        }

        Post lastPost = posts.get(posts.size() - 1);
        String nextPageId = String.valueOf(lastPost.getId());
        String nextPageKey = getNextPageKey(sortBy, lastPost);
        PostsCursor postsCursor = PostsCursor.builder()
                .pageId(nextPageId)
                .pageKey(nextPageKey)
                .sortBy(sortBy)
                .pageSize(pageSize)
                .direction(sortDir)
                .build();
        return postsCursor;
    }

    private String getNextPageKey(String sortBy, Post post) {
        switch (sortBy) {
            case "title":
                return post.getTitle();
            case "description":
                return post.getDescription();
            case "content":
                return post.getContent();
            case "id":
                return String.valueOf(post.getId());
            default:
                throw new IllegalArgumentException("Invalid sort by column provided");
        }
    }

    public PostsCursor processCursor(String cursor, String sortBy, String sortDir, int pageSize) {
        if (pageSize <= 0) {
            throw new IllegalArgumentException("Pagesize cannot be 0");
        }

        if (cursor.equalsIgnoreCase("")) {
            //create cursor
            String pageId = String.valueOf(Long.MIN_VALUE);
            String pageKey = getMinValue(sortBy);

            if (!sortDir.equalsIgnoreCase("ASC")) {
                pageKey = getMaxValue(sortBy);
                pageId = String.valueOf(Long.MAX_VALUE);
            }
            return PostsCursor.builder()
                    .sortBy(sortBy)
                    .direction(sortDir)
                    .pageKey(pageKey)
                    .pageId(pageId)
                    .pageSize(pageSize).build();

        } else {
            // decode cursor
            return PostsCursor.decodeCursorString(cursor)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid cursor provided"));
        }
    }


    public String getMaxValue(String column) {
        switch (column) {
            case "title":
            case "description":
                return "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
                        + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
                        + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
                        + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
                // MAX varchar(255)
            case "content":
                return "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
                        + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
                        + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
                        + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
                        + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
                        + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz"
                        + "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";
                // MAX varchar(500)
            default:
                return String.valueOf(Long.MAX_VALUE);
        }
    }

    public String getMinValue(String column) {
        switch (column) {
            case "title":
            case "description":
            case "content":
                return ""; //SMALLEST possible value
            default:
                return String.valueOf(Long.MIN_VALUE);
        }
    }


    public static void main(String args[]) {
        StringBuilder up = new StringBuilder();
        StringBuilder low = new StringBuilder();

        for (int i = 0; i < 500; i++) {
            up.append('Z');
        }

        for (int i = 0; i < 500; i++) {
            low.append('z');
        }

        if (up.compareTo(low) > 0) {
            System.out.println("Greatest string:" + up.toString());
        } else {
            System.out.println("Greatest string:" + low.toString());
        }
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
        return DataConvertor.postEntitiesToDto(postRepository.findByCategoryId(category.getId()));
    }


    public void testRun(Category category) {
        System.out.println(category.getId());
        System.out.println(category.getId());
    }
}
