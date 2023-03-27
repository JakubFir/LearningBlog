package com.example.LearningBlog.blogUser;

import com.example.LearningBlog.comments.Comment;
import com.example.LearningBlog.post.Post;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class BlogUser implements UserDetails{

    @SequenceGenerator(
            name = "blogUser_sequence",
            sequenceName = "blogUser_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            generator = "blogUser_sequence",
            strategy = GenerationType.SEQUENCE)
    @Id
    private Long userId;
    @Column
    private String username;
    @Column
    private String password;
    @OneToMany(
            targetEntity = Post.class,
            mappedBy = "blogUser",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Post> userPosts;

    public BlogUser(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    @OneToMany(
            targetEntity = Comment.class,
            mappedBy = "blogUser",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Comment> userComments;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public BlogUser(Long userId, String username, String password, Role role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

