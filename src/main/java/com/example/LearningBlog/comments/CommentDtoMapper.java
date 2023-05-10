package com.example.LearningBlog.comments;


import com.example.LearningBlog.comments.anonymousComments.AnonymousCommentDto;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CommentDtoMapper {
    public Comment mapAnonymousToDomain(AnonymousCommentDto anonymousCommentDto) {
        return new Comment(
                anonymousCommentDto.getCommentId(),
                anonymousCommentDto.getUsername(),
                anonymousCommentDto.getCommentBody(),
                new Date(),
                false,
                anonymousCommentDto.getPostId()
        );
    }

    public Comment mapDtoToDomain(CommentDto commentDto) {
        return new Comment(
                commentDto.getCommentId(),
                commentDto.getUsername(),
                commentDto.getCommentBody(),
                new Date(),
                commentDto.isAnonymous());
    }

    public CommentDto mapCommentToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getUsername(),
                comment.getCommentBody(),
                comment.getDateOfPublishing(),
                comment.isAnonymous());
    }

    public AnonymousCommentDto mapDomainToAnonymous(Comment comment) {
        return new AnonymousCommentDto(
                comment.getCommentId(),
                comment.getAnonymousId(),
                comment.getCommentBody(),
                comment.getUsername()
        );
    }
}
