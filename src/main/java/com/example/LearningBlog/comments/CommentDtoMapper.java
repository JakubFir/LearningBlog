package com.example.LearningBlog.comments;

import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CommentDtoMapper implements Function<Comment, CommentDto> {
    @Override
    public CommentDto apply(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getUsername(),
                comment.getCommentBody(),
                comment.getDateOfPublishing(),
                comment.getPost());
    }
}
