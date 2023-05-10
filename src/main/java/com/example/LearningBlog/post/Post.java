package com.example.LearningBlog.post;

import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.comments.Comment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Post {

    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "post_sequence",
            strategy = GenerationType.SEQUENCE)
    @Id
    private Long postId;
    @Column(length = 2000)
    private String post;
    @Column
    private String title;
    @Temporal(TemporalType.DATE)
    private Date dateOfPublishing = new Date();
    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "post",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Comment> postComments;

    private boolean translated;

    @Column
    private String translation;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonIgnoreProperties(value = "blogUser")
    private BlogUser blogUser;

    public Post(Long postId, String post, String title, String translation, Date dateOfPublishing) {
        this.postId = postId;
        this.post = post;
        this.title = title;
        this.translation = translation;
        this.dateOfPublishing = dateOfPublishing;

    }
}

