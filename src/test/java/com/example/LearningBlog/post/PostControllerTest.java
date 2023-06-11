package com.example.LearningBlog.post;

import com.example.LearningBlog.blogUser.BlogUserController;
import com.example.LearningBlog.security.JwtService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(PostController.class)
class PostControllerTest {
    @MockBean
    private PostMapper postMapper;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private PostService postService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser(authorities = "ADMIN")
    void getAllPosts() throws Exception {
        //Given

        Post post = new Post(1L,"Tes","test","test",new Date());
        List<Post> postList = new ArrayList<>();
        postList.add(post);

        when(postService.getAllPost()).thenReturn(postList);

        ///When && Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/blog/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void getPost() throws Exception {
        //Given
        Post post = new Post(1L,"Tes","test","test",new Date());
        when(postService.getPost(1L)).thenReturn(post);

        ///When && Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/api/v1/blog/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    @PreAuthorize("authenticated")
    void addPost() throws Exception {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.lgcL-Iq9T4iB3mMhlM1bquegMrnz-M-pjSWFQJB4giE";

        Post post = new Post(1L,"Tes","test","test",new Date());
        PostDto postDto = new PostDto(1L,"asd","asd","asd",true,new Date());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(postDto);

        //When && Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/v1/blog/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());


    }

    @Test
    void deletePost() {
    }

    @Test
    void updatePost() {
    }
}