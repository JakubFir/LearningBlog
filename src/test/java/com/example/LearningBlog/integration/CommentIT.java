package com.example.LearningBlog.integration;

import com.example.LearningBlog.blogUser.*;


import com.example.LearningBlog.conteiners.Testcontainers;
import com.example.LearningBlog.post.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;



import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc
public class CommentIT extends Testcontainers {

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
