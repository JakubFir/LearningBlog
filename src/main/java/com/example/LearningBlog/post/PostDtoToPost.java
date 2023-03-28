package com.example.LearningBlog.post;

import com.example.LearningBlog.comments.Comment;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class PostDtoToPost {


    public Post mapDtoToPost(PostDto postDto) {
        return new Post(
                postDto.getPostId(),
                postDto.getPost(),
                postDto.getTitle(),
                postDto.getDateOfPublishing(),
                postDto.getPostComments().stream().map(commentDto ->
                        new Comment(
                                commentDto.getCommentId(),
                                commentDto.getUsername(),
                                commentDto.getCommentBody(),
                                commentDto.getDateOfPublishing())).collect(Collectors.toList()));
    }
}
