package com.example.LearningBlog.comments;

import com.example.LearningBlog.post.Post;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Date;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public record CommentDto(
        Long commentId,
        String username,
        String commentBody,
        Date dateOfPublishing,
        Post post) {
}
