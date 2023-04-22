package com.example.LearningBlog.translator;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Translation {
    @Id
    @SequenceGenerator(
            name = "translation_sequence",
            sequenceName = "translation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "translation_sequence",
            strategy = GenerationType.SEQUENCE)
    private Long translationId;
    private String translatedPost;

    public Translation(String translatedPost) {
        this.translatedPost = translatedPost;
    }
}
