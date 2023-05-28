 package com.example.LearningBlog.comments.anonymousComments;

import com.example.LearningBlog.comments.Comment;
import com.example.LearningBlog.comments.CommentMapper;
import com.example.LearningBlog.comments.CommentRepository;
import com.example.LearningBlog.post.*;
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
class AnonymousCommetsServiceTest {

    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private CommentMapper commentMapper;
    @InjectMocks
    private PostMapper postMapper;
    @Mock
    private CommentRepository commentRepository;

    private AnonymousCommetsService anonymousCommetsService;
    private Post post;

    @BeforeEach
    void setUp() {
        anonymousCommetsService = new
                AnonymousCommetsService(commentMapper, commentRepository , postRepository);
        PostDto postDto = new PostDto(
                1L,
                "asd",
                "asd",
                null,
                false,
                new Date());
        post = postMapper.mapDtoToDomain(postDto);
    }

    @Test
    void addAnonymousComment() {
        //Given
        AnonymousCommentDto anonymousComment = new AnonymousCommentDto(1L, 1L,"ASD", "asd");

        //When
        anonymousCommetsService.addCommentToApprove(anonymousComment);
        ArgumentCaptor<Comment> anonymousCommentsArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(anonymousCommentsArgumentCaptor.capture());
        Comment anonymousComments2 = anonymousCommentsArgumentCaptor.getValue();

        //Then
        assertThat(anonymousComments2.getCommentBody()).isEqualTo(anonymousComment.getCommentBody());

    }

    @Test
    void getAllCommentsToApprove() {
        //Given
        Comment anonymousComment = new Comment(1L, "asd", "ASD", new Date(),true);
        List<Comment> anonymousComments = new ArrayList<>();
        anonymousComments.add(anonymousComment);

        when(commentRepository.findAll()).thenReturn(anonymousComments);

        //When
        List<Comment> anonymousComments1 = anonymousCommetsService.getAllCommentsToApprove();

        //Then
        assertThat(anonymousComments1.get(0).getCommentBody()).isEqualTo(anonymousComment.getCommentBody());

    }

    @Test
    void addAnonymousCommentToPost() {
        //Given
        Long id = 1L;
        List<Comment> comments = new ArrayList<>();
        Comment comment = new Comment(1L, "asd", "asd", new Date(),true);
        comments.add(comment);
        post.setPostComments(comments);
        when(postRepository.findById(id)).thenReturn(Optional.ofNullable(post));
        when(commentRepository.findById(id)).thenReturn(Optional.of(comment));
        AnonymousCommentDto anonymousComment = new AnonymousCommentDto(1L, 1L,"ASD", "asd");

        //When
        anonymousCommetsService.addAnonymousCommentToPost(anonymousComment);
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        verify(commentRepository).save(commentArgumentCaptor.capture());
        Comment capturedComment = commentArgumentCaptor.getValue();

        //Then
        assertThat(capturedComment.getUsername())
                .isEqualTo(post.getPostComments().get(1).getUsername());
        assertThat(post.getPostComments().get(1).getUsername()).isEqualTo("Anonymous");

    }
}

