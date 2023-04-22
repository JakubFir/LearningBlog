package com.example.LearningBlog.comments;

import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.blogUser.BlogUserService;
import com.example.LearningBlog.blogUser.Role;
import com.example.LearningBlog.post.Post;
import com.example.LearningBlog.post.PostDto;
import com.example.LearningBlog.post.PostMapper;
import com.example.LearningBlog.post.PostRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

class CommentServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private BlogUserService blogUserService;
    @InjectMocks
    private CommentDtoMapper commentDtoMapper;
    @Mock
    private CommentRepository commentRepository;
    private CommentService commentService;
    @InjectMocks
    private PostMapper postMapper;
    private BlogUser blogUser;
    private Post post;
    private PostDto postDto;
    private Long id;

    @BeforeEach
    void setUp() {
        commentService = new CommentService(postRepository, blogUserService, commentDtoMapper, commentRepository);
        blogUser = new BlogUser(1L, "rafal", "asd", Role.USER);
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
    void addCommentToPost() {
        //Given
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post));
        when(blogUserService.getBlogUser(1L)).thenReturn(blogUser);
        Comment comment = new Comment(1L, "asd", "asd", new Date());
        List<Comment> comments = new ArrayList<>();
        CommentDto commentDto = commentDtoMapper.mapCommentToCommentDto(comment);
        post.setPostComments(comments);

        //When
        commentService.addCommentToPost(id, commentDto, 1L);

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        Post capturedPost = postArgumentCaptor.getValue();

        //Then
        assertThat(capturedPost.getPostComments().size()).isEqualTo(1);

        assertThat(capturedPost.getPostComments().get(0).getCommentBody())
                .isEqualTo(commentDto.getCommentBody());

    }

    @Test
    void getPostComments() {
        //given
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment(1L, "asd", "asd", new Date());
        comments.add(comment);
        post.setPostComments(comments);
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post));

        //when
        List<CommentDto> result = commentService.getPostComments(id);

        //then
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void getComments() {
    }
}