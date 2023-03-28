package com.example.LearningBlog.post;

import com.example.LearningBlog.comments.CommentDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostDtoMapper {

    CommentDtoMapper commentDtoMapper;

    public PostDto mapPostToPostDto(Post post) {
        return new PostDto(
                post.getPostId(),
                post.getPost(),
                post.getTitle(),
                post.getDateOfPublishing(),
                post.getPostComments().stream().map(comment -> commentDtoMapper.mapCommentToCommentDto(comment)).collect(Collectors.toList()));
    }
}
