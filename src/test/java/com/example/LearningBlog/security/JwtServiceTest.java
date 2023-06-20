package com.example.LearningBlog.security;

import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.blogUser.BlogUserRepository;
import com.example.LearningBlog.blogUser.Role;
import com.example.LearningBlog.post.Post;
import io.jsonwebtoken.io.Decoders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {


    @Mock
    private BlogUserRepository blogUserRepository;
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService(blogUserRepository);
    }

    @Test
    public String generateToken() {
        List<Post> posts = new ArrayList<>();
        BlogUser blogUser = new BlogUser("rafal", Role.USER, posts);
        when(blogUserRepository.findBlogUsersByUsername("asd")).thenReturn(Optional.of(blogUser));
        String token = jwtService.generateToken("asd");
        return token;
    }
}