package com.example.LearningBlog.comments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long commentId;
    private String username;
    private String commentBody;
    private Date dateOfPublishing;
    private boolean anonymous;


}
