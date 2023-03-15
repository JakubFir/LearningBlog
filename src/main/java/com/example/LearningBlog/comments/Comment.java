package com.example.LearningBlog.comments;

import com.example.LearningBlog.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Comment {
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "comment_sequence",
            strategy = GenerationType.SEQUENCE)
    @Id
    private Long commentId;
    @Column
    private String username;
    @Column
    private String commentBody;
    @Temporal(TemporalType.DATE)
    private Date dateOfPublishing = new Date();
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    @JsonIgnoreProperties("postComments")
    private Post post;


}
