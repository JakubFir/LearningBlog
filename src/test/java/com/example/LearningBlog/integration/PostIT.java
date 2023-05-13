package com.example.LearningBlog.integration;

import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.blogUser.BlogUserRepository;
import com.example.LearningBlog.blogUser.RegisterRequest;
import com.example.LearningBlog.errorHandler.PostNotFoundException;
import com.example.LearningBlog.post.Post;
import com.example.LearningBlog.post.PostDto;
import com.example.LearningBlog.post.PostMapper;
import com.example.LearningBlog.post.PostRepository;
import com.example.LearningBlog.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import org.springframework.security.core.parameters.P;
import org.springframework.test.context.TestPropertySource;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class PostIT {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BlogUserRepository blogUserRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository postRepository;


    @Test
    @Transactional
    public void addPost() throws Exception {
        //Given
        RegisterRequest request = new RegisterRequest("Rafał", "asd");
        List<Post> list = new ArrayList<>();
        PostDto postDto = new PostDto(1L, "asd", "asd", "asd", false, new Date());

        //When
        ResultActions perform1 = mockMvc.perform(post("/api/v1/blog/users/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        BlogUser blogUser = blogUserRepository.findBlogUsersByUsername(request.getUsername()).orElseThrow();
        blogUser.setUserPosts(list);
        String token = jwtService.generateToken(request.getUsername());

        ResultActions perform = mockMvc.perform(post("/api/v1/blog/posts/{blogUserId}", blogUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk());

        //Then
        Post savedPost = postRepository.findById(postDto.getPostId()).orElseThrow();
        assertThat(savedPost).isNotNull();
        assertThat(blogUser.getUserPosts().get(0).getPost()).isEqualTo("asd");
        assertThat(blogUser.getUserPosts().size()).isEqualTo(1);

        postRepository.deleteById(savedPost.getPostId());
        blogUser.setUserPosts(null);
        blogUserRepository.deleteById(blogUser.getUserId());

    }

    @Test
    @Transactional
    void getPost() throws Exception {
        //Given
        RegisterRequest request = new RegisterRequest("Rafał", "asd");
        List<Post> list = new ArrayList<>();
        PostDto postDto = new PostDto(2L, "asd", "asd", "asd", false, new Date());

        //When
        ResultActions perform1 = mockMvc.perform(post("/api/v1/blog/users/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        BlogUser blogUser = blogUserRepository.findBlogUsersByUsername(request.getUsername()).orElseThrow();
        blogUser.setUserPosts(list);
        String token = jwtService.generateToken(request.getUsername());

        ResultActions perform = mockMvc.perform(post("/api/v1/blog/posts/{blogUserId}", blogUser.getUserId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(status().isOk());

        Post savedPost = postRepository.findById(postDto.getPostId()).orElseThrow();

        assertThat(savedPost).isNotNull();

        MvcResult getPost = mockMvc.perform(get("/api/v1/blog/posts/{postId}", savedPost.getPostId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String response = getPost.getResponse().getContentAsString();
        PostDto actual = objectMapper.readValue(response, PostDto.class);

        //Then
        assertThat(postDto.getTitle()).isEqualTo(actual.getTitle());

        postRepository.deleteById(actual.getPostId());
    }

    @Test
    void delete() {


    }


}
