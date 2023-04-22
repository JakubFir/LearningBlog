package com.example.LearningBlog.translator;

import org.springframework.stereotype.Service;

@Service
public class TranslationDtoMapper {
    public TranslationDto mapToDto(Translation translation) {
        return new TranslationDto(
                translation.getTranslatedPost()
        );
    }

    public Translation mapToDomain(TranslationDto translation) {
        return new Translation(
                translation.getTransletedContent()
        );
    }
}
