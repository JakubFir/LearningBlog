package com.example.LearningBlog.post;

import com.example.LearningBlog.comments.CommentDto;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private String post;
    private String title;
    private Date dateOfPublishing;
    private List<CommentDto> postComments;

}
