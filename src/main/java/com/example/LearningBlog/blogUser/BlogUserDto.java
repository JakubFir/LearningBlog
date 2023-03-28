package com.example.LearningBlog.blogUser;
import com.example.LearningBlog.post.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BlogUserDto {
    private String username;
    private Role role;
    private List<PostDto> usersPosts;
}
