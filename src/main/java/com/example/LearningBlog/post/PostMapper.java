package com.example.LearningBlog.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@AllArgsConstructor
public class PostMapper {
    public Post mapDtoToDomain(PostDto postDto) {
        return new Post(
                postDto.getPostId(),
                postDto.getPost(),
                postDto.getTitle(),
                postDto.getTranslation(),
                new Date());
    }

    public PostDto mapPostToPostDto(Post post) {
        return new PostDto(
                post.getPostId(),
                post.getPost(),
                post.getTitle(),
                post.getTranslation(),
                post.isTranslated(),
                post.getDateOfPublishing());
    }
}
