package com.example.LearningBlog.post;


import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private String post;
    private String title;
    private String translation;
    private boolean translated;
    private Date dateOfPublishing;


}
