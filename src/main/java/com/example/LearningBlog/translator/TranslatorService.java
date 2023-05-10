package com.example.LearningBlog.translator;


import com.example.LearningBlog.azureTranslator.AzureClient;
import com.example.LearningBlog.azureTranslator.TranslationDto;
import com.example.LearningBlog.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslatorService {
    private final AzureClient azureClient;
    private final PostService postService;
    private final PostRepository postRepository;

    public void translatePost(Long id) throws IOException {
        Post post = postService.getPost(id);
        TranslationDto translationDto = new TranslationDto(post.getPost());
        if (post.getTranslation() == null) {
            String translatedContent = azureClient.translate(translationDto);
            post.setTranslation(translatedContent);
        }
        post.setTranslated(true);
        postRepository.save(post);
    }

    public void getOriginalPosts() {
        List<Post> originalPosts = postRepository.findAll();
        for (Post post : originalPosts) {
            post.setTranslated(false);
            postRepository.save(post);
        }
    }
}
