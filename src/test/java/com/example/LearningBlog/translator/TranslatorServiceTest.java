package com.example.LearningBlog.translator;

import com.example.LearningBlog.azureTranslator.AzureClient;
import com.example.LearningBlog.azureTranslator.TranslationDto;
import com.example.LearningBlog.post.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TranslatorServiceTest {

    @InjectMocks
    private PostMapper postMapper;
    @Mock
    private AzureClient azureClient;
    @Mock
    private PostService postService;
    @Mock
    private PostRepository postRepository;

    private TranslatorService translatorService;
    private Post post;
    private Long id;


    @BeforeEach
    void setUp() {
        translatorService = new TranslatorService( azureClient, postService, postRepository);
        PostDto postDto = new PostDto(
                1L,
                "asd",
                "asd",
                null,
                false,
                new Date());
        post = postMapper.mapDtoToDomain(postDto);
        id = 1L;
        TranslationDto translationDto = new TranslationDto("Witam");
    }

    @Test
    void translatePost() throws IOException {
        //Given
        when(postService.getPost(id)).thenReturn(post);

        //When
        translatorService.translatePost(id);

        ArgumentCaptor<Post> postArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        Post capturedPost = postArgumentCaptor.getValue();

        //then
        assertThat(capturedPost.isTranslated()).isTrue();

    }

    @Test
    void getOriginalPosts() {
    }
}