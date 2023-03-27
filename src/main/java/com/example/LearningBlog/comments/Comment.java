package com.example.LearningBlog.comments;

import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.post.Post;
import jakarta.persistence.*;
import lombok.*;
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
    private Post post;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private BlogUser blogUser;

    public Comment(Long commentId, String username, String commentBody, Date dateOfPublishing) {
        this.commentId = commentId;
        this.username = username;
        this.commentBody = commentBody;
        this.dateOfPublishing = dateOfPublishing;

    }
}
