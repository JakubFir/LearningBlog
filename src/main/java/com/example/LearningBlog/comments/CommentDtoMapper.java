package com.example.LearningBlog.comments;


import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class CommentDtoMapper {

    public Comment mapDtoToDomain(CommentDto commentDto){
        return new Comment(
                commentDto.getCommentId(),
                commentDto.getUsername(),
                commentDto.getCommentBody(),
                new Date());
    }
    public CommentDto mapCommentToCommentDto(Comment comment) {
        return new CommentDto(
                comment.getCommentId(),
                comment.getUsername(),
                comment.getCommentBody(),
                comment.getDateOfPublishing());
    }
}
