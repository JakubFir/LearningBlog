package com.example.LearningBlog.blogUser;

import com.example.LearningBlog.post.Post;
import com.example.LearningBlog.post.PostDto;
import com.example.LearningBlog.post.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogUserServiceTest {
    private BlogUser blogUser;
    private Long id;
    private RegisterRequest request;
    private BlogUserService blogUserService;
    @Mock
    private BlogUserRepository blogUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    void setUp() {
        blogUserService = new BlogUserService(blogUserRepository, passwordEncoder);
    }

    @Test
    void addBlogUser() {
        //Given
        List<Post> posts = new ArrayList<>();
        blogUser = new BlogUser("rafal", Role.USER, posts);

        //When
        blogUserService.addBlogUser(blogUser);

        //Then
        ArgumentCaptor<BlogUser> blogUserArgumentCaptor = ArgumentCaptor.forClass(BlogUser.class);
        verify(blogUserRepository).save(blogUserArgumentCaptor.capture());

        BlogUser capturedBlogUser = blogUserArgumentCaptor.getValue();

        assertThat(capturedBlogUser.getUsername()).isEqualTo(blogUser.getUsername());
        assertThat(capturedBlogUser.getPassword()).isEqualTo(blogUser.getPassword());
        assertThat(capturedBlogUser.getRole()).isEqualTo(blogUser.getRole());
    }

    @Test
    void getBlogUser() {
        //Given
        List<Post> posts = new ArrayList<>();
        id = 10L;
        blogUser = new BlogUser("rafal", Role.USER, posts);
        //When
        when(blogUserRepository.findById(id)).thenReturn(Optional.of(blogUser));
        BlogUser expected = blogUserService.getBlogUser(id);

        //Then
        assertThat(expected).isEqualTo(blogUser);
    }

    @Test
    void testGetAllBlogUsers() {
        // Given
        List<Post> posts = new ArrayList<>();
        blogUser = new BlogUser("rafal", Role.USER, posts);
        List<BlogUser> blogUserList = new ArrayList<>();
        blogUserList.add(blogUser);
        when(blogUserRepository.findAll()).thenReturn(blogUserList);

        //When
        List<BlogUser> blogUser1 = blogUserService.getAllBlogUsers();

        //Then
        assertThat(blogUser1.get(0).getUsername()).isEqualTo(blogUser.getUsername());
    }

    @Test
    void registerUser() {
        //Given
        request = new RegisterRequest("asd", "asd");
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());

        //When
        blogUserService.registerUser(request);

        ArgumentCaptor<BlogUser> blogUserArgumentCaptor = ArgumentCaptor.forClass(BlogUser.class);
        verify(blogUserRepository).save(blogUserArgumentCaptor.capture());

        BlogUser capturedBlogUser = blogUserArgumentCaptor.getValue();

        //Then
        assertThat(capturedBlogUser.getUsername()).isEqualTo(request.getUsername());
        assertThat(capturedBlogUser.getPassword()).isEqualTo(request.getPassword());
        assertThat(capturedBlogUser.getRole()).isEqualTo(Role.USER);

    }

    @Test
    void registerAdmin() {
        request = new RegisterRequest("asd", "asd");
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());

        //When
        blogUserService.registerAdmin(request);

        ArgumentCaptor<BlogUser> blogUserArgumentCaptor = ArgumentCaptor.forClass(BlogUser.class);
        verify(blogUserRepository).save(blogUserArgumentCaptor.capture());
        BlogUser capturedBlogUser = blogUserArgumentCaptor.getValue();

        //Then
        assertThat(capturedBlogUser.getUsername()).isEqualTo(request.getUsername());
        assertThat(capturedBlogUser.getPassword()).isEqualTo(request.getPassword());
        assertThat(capturedBlogUser.getRole()).isEqualTo(Role.ADMIN);

    }
}