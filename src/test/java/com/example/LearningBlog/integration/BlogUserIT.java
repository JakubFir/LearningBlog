package com.example.LearningBlog.integration;


import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.blogUser.BlogUserRepository;
import com.example.LearningBlog.blogUser.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
public class BlogUserIT {

        @Autowired
        private MockMvc mockMvc;
        @Autowired
        private  BlogUserRepository blogUserRepository;
        @Autowired
        private ObjectMapper objectMapper;

    @Test
    void name() throws Exception {
        //given
        BlogUser blogUser = new BlogUser("rafal", "asd", Role.USER);
        //when
        ResultActions perform = mockMvc.perform(post("/api/v1/blog/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(blogUser)));
        //then
        perform.andExpect(status().isOk());
      BlogUser blogUser1 = blogUserRepository.findBlogUsersByUsername(blogUser.getUsername()).orElseThrow();
      assertThat(blogUser1.getUsername()).isEqualTo(blogUser.getUsername());
    }
}

