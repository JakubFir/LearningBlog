package com.example.LearningBlog.post;


import com.example.LearningBlog.blogUser.BlogUserRepository;
import com.example.LearningBlog.blogUser.BlogUserService;

import com.example.LearningBlog.comments.Comment;
import com.example.LearningBlog.comments.CommentDto;
import com.example.LearningBlog.comments.CommentDtoMapper;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    private PostService postService;
    @Mock
    private BlogUserRepository blogUserRepository;
    @Mock
    private BlogUserService blogUserService;
    @Mock
    private PostRepository postRepository;
    private PostDtoToPost toPost = new PostDtoToPost();
    private CommentDtoMapper commentDtoMapper = new CommentDtoMapper();
    private PostDtoMapper postDtoMapper = new PostDtoMapper(commentDtoMapper);

    @BeforeEach
    void setUp() {
        postService = new PostService(blogUserService, postRepository, commentDtoMapper, postDtoMapper);
    }

    @Test
    void getAllPost() {
    }

    @Test
    void getPost() {
        List<CommentDto> commentList = new ArrayList<>();
        Long id = 10L;
        PostDto postDto = new PostDto(id, "asd", "asd", new Date(), commentList);
        Post post = toPost.mapDtoToPost(postDto);
        when(postRepository.findById(id)).thenReturn(Optional.of(post));
        //when
        PostDto result = postService.getPost(id);
        Post resultt = toPost.mapDtoToPost(result);

        assertThat(post).isEqualTo(resultt);
    }

    @Test
    void addPost() {
        List<Comment> comments = new ArrayList<>();
        Long id = 10L;
        Post post = new Post(1L, "ASD", "asd", new Date(), comments);
        //when
        postService.addPost(post, id);
        //then
        verify(blogUserService).getBlogUser(id);
        verify(blogUserService).savePost(id, post);
        verify(postRepository).save(post);


    }

    @Test
    void deletePost() {
    }

    @Test
    void updatePost() {
    }

    @Test
    void getPostComments() {
    }

    @Test
    void addCommentToPost() {
    }
}