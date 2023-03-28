package com.example.LearningBlog.comments;


import org.springframework.stereotype.Service;


@Service
public class CommentDtoMapper {

    public CommentDto mapCommentToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getCommentBody(),
                comment.getUsername(),
                comment.getDateOfPublishing());

    }
}
