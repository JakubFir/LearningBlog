package com.example.LearningBlog.blogUser;
import com.example.LearningBlog.post.PostDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlogUserDtoMapper {
    @Autowired
    private final  PostDtoMapper postDtoMapper;

    public BlogUserDto mapBlogUserToBlogUserDto(BlogUser blogUser) {
        return new BlogUserDto(
                blogUser.getUsername(),
                blogUser.getRole(),
                blogUser.getUserPosts().stream().map(postDtoMapper::mapPostToPostDto).collect(Collectors.toList()));
    }
}
