package com.springboot.blog.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostResponse;
import com.springboot.blog.entity.Post;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.util.MockDataGenerator;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PostServiceImpl postService;

//    @BeforeEach
//    void setUp() {
//
//    }

//    @AfterEach
//    void tearDown() {
//    }

    @Test
    void getPosts() {
        // Method-1 create a mock of Page and use this as response in mockito
        Page<Post> mockPage = Mockito.mock(Page.class);

        // this line means when we make a call to a findAll(Pageable.class) type function of repo
        // then return a mock response of postPage
        // this mock we create in many ways
        when(postRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
        List<PostDto> postDtos = postService.getPosts(0, 10);
        Assertions.assertNotNull(postDtos);
        Assertions.assertEquals(0, postDtos.size());


        //Method - 2 create an instance of Page and override all its method
        List<Post> posts = MockDataGenerator.getMockPosts();
        Page<Post> postPage = MockDataGenerator.getMockPostPage(posts);
        when(postRepository.findAll(any(Pageable.class))).thenReturn(postPage);
        List<PostDto> postDtos2 = postService.getPosts(0, 10);
        Assertions.assertNotNull(postDtos2);
        Assertions.assertEquals(1, postDtos2.size());

    }

    @Test
    void getAllPosts() {
        List<Post> posts = MockDataGenerator.getMockPosts();
        Page<Post> pagePost = MockDataGenerator.getMockPostPage(posts);
        Page<Post> mockPage = Mockito.mock(Page.class);
        doReturn(mockPage).when(postRepository).findAll(any(Pageable.class));
//        when(postRepository.findAll(any(Pageable.class))).thenReturn(mockPage);
        PostResponse postResponse = postService.getAllPosts(0, 10, "title", "ASC");
        Assertions.assertNotNull(postResponse);
        Assertions.assertEquals(0, postResponse.getContent().size());
        Assertions.assertEquals(10, postResponse.getPageSize());
    }

//    @Test
    void createPost() {
    }

    @Test
    void getPostById() {
    }

//    @Test
    void updatePost() {
    }

//    @Test
    void deletePost() {
    }

//    @Test
    void getPostsByCategory() {
    }
}