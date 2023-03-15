package com.example.LearningBlog.post;


import com.example.LearningBlog.comments.Comment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PostDtoMapper implements Function<Post,PostDto>{
    @Override
    public PostDto apply(Post post) {
        return new PostDto(
                post.getPostId(),
                post.getTitle(),
                post.getPost(),
                post.getDateOfPublishing(),
                post.getPostComments());
    }
}
