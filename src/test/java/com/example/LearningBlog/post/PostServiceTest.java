package com.example.LearningBlog.post;

import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.blogUser.BlogUserRepository;
import com.example.LearningBlog.blogUser.BlogUserService;
import com.example.LearningBlog.blogUser.Role;
import com.example.LearningBlog.comments.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private BlogUserService blogUserService;
    @Mock
    private PostRepository postRepository;


    @InjectMocks
    private PostMapper postMapper;

    private PostService postService;
    private Post post;
    private PostDto postDto;
    private Long id;

    @BeforeEach
    void setUp() {
        postService = new PostService(blogUserService, postRepository,postMapper);
        postDto = new PostDto(
                1L,
                "asd",
                "asd",
                null,
                false,
                new Date());
        post = postMapper.mapDtoToDomain(postDto);
        id = 1L;
    }

    @Test
    void getAllPost() {
        //Given
        List<Post> list = new ArrayList<>();
        list.add(post);
        when(postRepository.getPostsInDescOrder()).thenReturn(list);

        //When
        List<Post> postDtos = postService.getAllPost();

        //Then
        assertThat(postDtos.get(0).getTitle()).isEqualTo(post.getTitle());

    }

    @Test
    void getPost() {
        //given
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post));

        //when
        Post result = postService.getPost(id);

        //then
        assertThat(result.getPost()).isEqualTo(postDto.getPost());
        assertThat(result.getTitle()).isEqualTo(postDto.getTitle());

    }

    @Test
    void addPost() {
        //given
        BlogUser blogUser = new BlogUser("rafal", "asd", Role.USER);
        List<Post> posts = new ArrayList<>();
        posts.add(post);

        when(blogUserService.getBlogUser(id)).thenReturn(blogUser);
        blogUser.setUserPosts(posts);
        //when
        postService.addPost(postDto, id);
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        Post capturedPost = postArgumentCaptor.getValue();

        //then
        assertThat(capturedPost.getPostId()).isEqualTo(postDto.getPostId());
        assertThat(capturedPost.getPost()).isEqualTo(postDto.getPost());

    }

    @Test
    void deletePost() {
        //given

        // when
        postService.deletePost(id);

        //then
        verify(postRepository).deleteById(id);
    }

    @Test
    void updatePost() {
        //given

        when(postRepository.findById(id)).thenReturn(Optional.of(post));

        //when
        postService.updatePost(id, postDto);
        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        Post capturedPost = postArgumentCaptor.getValue();

        //then
        assertThat(capturedPost.getPost()).isEqualTo(postDto.getPost());
        assertThat(capturedPost.getPostId()).isEqualTo(1L);
    }




}