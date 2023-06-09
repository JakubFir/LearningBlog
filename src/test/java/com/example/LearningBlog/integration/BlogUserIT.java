package com.example.LearningBlog.integration;


import com.example.LearningBlog.blogUser.*;
import com.example.LearningBlog.conteiners.Testcontainers;
import com.example.LearningBlog.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class BlogUserIT extends Testcontainers {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BlogUserRepository blogUserRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private JwtService jwtService;

    @Test
    void addUserTest() throws Exception {
        //Given
        RegisterRequest request = new RegisterRequest("Rafał", "asd");

        //When
        ResultActions perform = mockMvc.perform(post("/api/v1/blog/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //Then
        perform.andExpect(status().isOk());
        BlogUser blogUser1 = blogUserRepository.findBlogUsersByUsername(request.getUsername()).orElseThrow();
        assertThat(blogUser1.getUsername()).isEqualTo(request.getUsername());
        assertThat(blogUser1.getRole()).isEqualTo(Role.USER);
        blogUserRepository.deleteById(blogUser1.getUserId());
    }

    @Test
    void addAdmin() throws Exception {
        //Given
        RegisterRequest request = new RegisterRequest("Rafał", "asd");

        //When
        ResultActions perform = mockMvc.perform(post("/api/v1/blog/users/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //Then
        perform.andExpect(status().isOk());
        BlogUser blogUser1 = blogUserRepository.findBlogUsersByUsername(request.getUsername()).orElseThrow();
        assertThat(blogUser1.getUsername()).isEqualTo(request.getUsername());
        assertThat(blogUser1.getRole()).isEqualTo(Role.ADMIN);
        blogUserRepository.deleteById(blogUser1.getUserId());
    }

    @Test
    void updateRole() throws Exception {
        //Given
        RegisterRequest request = new RegisterRequest("Rafał", "asd");
        BlogUserDto blogUserDto = new BlogUserDto("asd", Role.USER, null);

        //When

         mockMvc.perform(post("/api/v1/blog/users/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        BlogUser blogUserToUpdate = blogUserRepository.findBlogUsersByUsername(request.getUsername()).orElseThrow();

        ResultActions perform2 = mockMvc.perform(put("/api/v1/blog/users/{userId}", blogUserToUpdate.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(blogUserDto)));

        BlogUser resultBlogUser = blogUserRepository.findBlogUsersByUsername(request.getUsername()).orElseThrow();
        //Then
        perform2.andExpect(status().isOk());
        assertThat(resultBlogUser.getUsername()).isEqualTo(request.getUsername());
        assertThat(resultBlogUser.getRole()).isEqualTo(Role.USER);
        blogUserRepository.deleteById(resultBlogUser.getUserId());
    }

    @Test
    void delete() throws Exception {
        //Given
        RegisterRequest request = new RegisterRequest("Rafał", "asd");

        //When
         mockMvc.perform(post("/api/v1/blog/users/admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        BlogUser blogUser1 = blogUserRepository.findBlogUsersByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(request.getUsername());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/blog/users/{userId}", blogUser1.getUserId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Then
        Optional<BlogUser> exists2 = blogUserRepository.findBlogUsersByUsername(request.getUsername());
        assertThat(exists2).isEmpty();
    }


}



