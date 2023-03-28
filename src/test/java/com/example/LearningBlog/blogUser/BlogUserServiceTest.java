package com.example.LearningBlog.blogUser;

import com.example.LearningBlog.comments.CommentDto;
import com.example.LearningBlog.comments.CommentDtoMapper;
import com.example.LearningBlog.post.Post;
import com.example.LearningBlog.post.PostDto;
import com.example.LearningBlog.post.PostDtoMapper;
import com.example.LearningBlog.post.PostDtoToPost;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogUserServiceTest {

    private BlogUserService blogUserService;
    @Mock
    private BlogUserRepository blogUserRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private final CommentDtoMapper commentDtoMapper = new CommentDtoMapper();
    private final PostDtoMapper postDtoMapper = new PostDtoMapper(commentDtoMapper);
    private final BlogUserDtoMapper blogUserDtoMapper = new BlogUserDtoMapper(postDtoMapper);


    @BeforeEach
    void setUp() {
        blogUserService = new BlogUserService(blogUserRepository, passwordEncoder, blogUserDtoMapper);
    }

    @Test
    void addBlogUser() {
        //given
        BlogUser blogUser = new BlogUser("rafal", "asd", Role.USER);
        //when
        blogUserService.addBlogUser(blogUser);
        //then
        ArgumentCaptor<BlogUser> blogUserArgumentCaptor = ArgumentCaptor.forClass(BlogUser.class);
        verify(blogUserRepository).save(blogUserArgumentCaptor.capture());

        BlogUser capturedBlogUser = blogUserArgumentCaptor.getValue();

        assertThat(capturedBlogUser.getUsername()).isEqualTo(blogUser.getUsername());
        assertThat(capturedBlogUser.getPassword()).isEqualTo(blogUser.getPassword());
        assertThat(capturedBlogUser.getRole()).isEqualTo(blogUser.getRole());
    }

    @Test
    void getBlogUser() {
        //given
        Long id = 10L;
        BlogUser blogUser = new BlogUser(id, "rafal", "asd", Role.USER);
        //when
        when(blogUserRepository.findById(id)).thenReturn(Optional.of(blogUser));
        BlogUser expected = blogUserService.getBlogUser(id);
        //then
        assertThat(expected).isEqualTo(blogUser);
    }

    @Test
    void getAllBlogUsers() {

    }

    @Test
    void savePost() {
        //given
        Long id = 10L;
        List<CommentDto> comments = new ArrayList<>();
        BlogUser blogUser = new BlogUser(id, "rafal", "asd", Role.USER);
        PostDto post = new PostDto(
                1L,
                "asd",
                "asd",
                new Date(),
                comments);


        PostDto post2 = new PostDto(
                2L,
                "asd",
                "asd",
                new Date(),
                comments);

        PostDtoToPost postDtoToPost = new PostDtoToPost();
        Post resultPost = postDtoToPost.mapDtoToPost(post);
        Post resultPost2 = postDtoToPost.mapDtoToPost(post2);

        List<Post> listPosts = new ArrayList<>();
        listPosts.add(resultPost);
        blogUser.setUserPosts(listPosts);

        when(blogUserRepository.findById(id)).thenReturn(Optional.of(blogUser));
        //when
        blogUserService.savePost(id, resultPost2);
        //then
        int size = blogUser.getUserPosts().size();
        assertEquals(2,size);
    }

    @Test
    void registerUser() {
        //given
        RegisterRequest request = new RegisterRequest("asd","asd");
        when(passwordEncoder.encode(request.getPassword())).thenReturn(request.getPassword());
        //when
        blogUserService.registerUser(request);

        ArgumentCaptor<BlogUser> blogUserArgumentCaptor = ArgumentCaptor.forClass(BlogUser.class);
        verify(blogUserRepository).save(blogUserArgumentCaptor.capture());

        BlogUser capturedBlogUser = blogUserArgumentCaptor.getValue();
        //then
        assertThat(capturedBlogUser.getUsername()).isEqualTo(request.getUsername());
        assertThat(capturedBlogUser.getPassword()).isEqualTo(request.getPassword());
    }
}