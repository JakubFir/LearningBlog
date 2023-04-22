package com.example.LearningBlog.translator;


import com.example.LearningBlog.azureTranslator.AzureClient;
import com.example.LearningBlog.azureTranslator.TranslationDto;
import com.example.LearningBlog.kafka.config.MessageController;
import com.example.LearningBlog.post.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslatorService {

    private final PostMapper postMapper;
    private final AzureClient azureClient;
    private final PostService postService;
    private final PostRepository postRepository;

    public void translatePost(TranslationDto text, Long id) throws IOException {
        PostDto postDto = postService.getPost(id);
        Post post = postMapper.mapDtoToDomain(postDto);
        post.setTranslated(true);
        if (post.getTranslation() == null) {
            String translatedContent = azureClient.translate(text);
            post.setTranslation(translatedContent);
        }
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
