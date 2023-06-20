package com.example.LearningBlog.blogUser;

import com.example.LearningBlog.auth.AuthenticationRequest;
import com.example.LearningBlog.post.Post;
import com.example.LearningBlog.security.JwtService;
import com.example.LearningBlog.security.JwtServiceTest;
import io.jsonwebtoken.Jwt;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@ContextConfiguration
@WebMvcTest(BlogUserController.class)
//@WithMockUser(username = "admin", authorities = {"ADMIN"})
class BlogUserControllerTest extends JwtServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BlogUserService blogUserService;
    @MockBean
    private BlogUserRepository blogUserRepository;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private BlogUserDtoMapper blogUserDtoMapper;

    @Test
    void getBlogUsers() {
    }

    @Test
    void updateBlogUserRole() {
    }

    @Test
    void addAdmin() {
    }

    @Test
    void addUser() {
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})

    void deleteUser() throws Exception {
        //Given

        //When && Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/v1/blog/users/1")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }
}