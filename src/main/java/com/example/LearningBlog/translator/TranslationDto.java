package com.example.LearningBlog.translator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class TranslationDto {
    private String transletedContent;
}
