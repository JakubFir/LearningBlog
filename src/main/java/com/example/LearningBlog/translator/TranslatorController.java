package com.example.LearningBlog.translator;
import com.example.LearningBlog.azureTranslator.TranslationDto;
import lombok.AllArgsConstructor;


import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping(path = "api/v1/translator")
@AllArgsConstructor
public class TranslatorController {
    private final TranslatorService translatorService;

    @PostMapping(path = "{id}")
    public void translator(@RequestBody TranslationDto text, @PathVariable Long id) throws IOException {
        translatorService.translatePost(text, id);
    }

    @GetMapping
    public void getTranslatedPosts() {
        translatorService.getOriginalPosts();

    }

}
