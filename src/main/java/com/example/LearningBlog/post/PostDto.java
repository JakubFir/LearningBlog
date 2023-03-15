package com.example.LearningBlog.post;

import com.example.LearningBlog.comments.Comment;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


import java.util.Date;
import java.util.List;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record PostDto(Long postId,
                      String title,
                      String post,
                      Date dateOfPublishing,
                      List<Comment> postComments){
}
