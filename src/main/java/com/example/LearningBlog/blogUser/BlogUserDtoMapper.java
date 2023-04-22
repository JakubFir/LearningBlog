package com.example.LearningBlog.blogUser;
import com.example.LearningBlog.post.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlogUserDtoMapper {
    private final PostMapper postMapper;

    public BlogUser mapDtoToDomain(BlogUserDto blogUserDto){
        return new BlogUser(
                blogUserDto.getUsername(),
                blogUserDto.getRole(),
                blogUserDto.getUsersPosts().stream().map(postMapper::mapDtoToDomain).collect(Collectors.toList())
                );
    }
    public BlogUserDto mapBlogUserToBlogUserDto(BlogUser blogUser) {
        return new BlogUserDto(
                blogUser.getUsername(),
                blogUser.getRole(),
                blogUser.getUserPosts().stream().map(postMapper::mapPostToPostDto).collect(Collectors.toList()));
    }
}
