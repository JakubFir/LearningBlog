package com.example.LearningBlog.comments.anonymousComments;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class AnonymousComments {
    @SequenceGenerator(
            name = "anonymousComment_sequence",
            sequenceName = "anonymousComment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "anonymousComment_sequence",
            strategy = GenerationType.SEQUENCE)
    @Id
    private Long commentId;
    private Long postId;
    private String commentBody;
    private String username;
    @Temporal(TemporalType.DATE)
    private Date dateOfPublishing = new Date();

    public AnonymousComments(Long postId, String commentBody, String username, Date dateOfPublishing) {
        this.postId = postId;
        this.commentBody = commentBody;
        this.username = username;
        this.dateOfPublishing = dateOfPublishing;

    }
}
