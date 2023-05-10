package com.example.LearningBlog.comments.anonymousComments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnonymousCommentDto {
    private Long commentId;
    private Long postId;
    private String commentBody;
    private String username;
}
