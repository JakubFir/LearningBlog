package com.example.LearningBlog.integration;

import com.example.LearningBlog.blogUser.*;

import com.example.LearningBlog.post.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class CommentIT {

    @Autowired
    private MockMvc mockMvc;
    @Mock
    private BlogUserService blogUserRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addCommentToPostTest() throws Exception {
        // Given

        // Then
    //    perform.andExpect(status().isOk());
    }
}
