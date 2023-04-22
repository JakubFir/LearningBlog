package com.example.LearningBlog.comments.anonymousComments;

import com.example.LearningBlog.comments.Comment;
import com.example.LearningBlog.comments.CommentDto;
import com.example.LearningBlog.comments.CommentDtoMapper;
import com.example.LearningBlog.comments.CommentRepository;
import com.example.LearningBlog.post.*;
import org.checkerframework.checker.units.qual.C;
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
class AnonymousCommetsServiceTest {

    @Mock
    private PostService postService;

    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private CommentDtoMapper commentDtoMapper;
    @InjectMocks
    private PostMapper postMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private AnonymousCommentsRepository anonymousCommentsRepository;

    private AnonymousCommetsService anonymousCommetsService;

    private Post post;
    private PostDto postDto;
    private Long id;

    @BeforeEach
    void setUp() {
        anonymousCommetsService = new
                AnonymousCommetsService(commentDtoMapper, commentRepository, anonymousCommentsRepository, postRepository);
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
    void getAllCommentsToApprove() {

    }

    @Test
    void addAnonymousCommentToPost() {
        //Given
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment(1L, "asd", "asd", new Date());
        comments.add(comment);
        post.setPostComments(comments);
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post));

        CommentDto commentDto = new CommentDto(id, "asd", "asd", new Date());

        //When
        anonymousCommetsService.addAnonymousCommentToPost(id, commentDto, id);
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(commentArgumentCaptor.capture());
        Comment capturedComment = commentArgumentCaptor.getValue();

        //Then
        assertThat(capturedComment.getUsername())
                .isEqualTo(post.getPostComments().get(1).getUsername());

    }
}