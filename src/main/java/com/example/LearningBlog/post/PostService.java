package com.example.LearningBlog.post;

import com.example.LearningBlog.comments.Comment;
import com.example.LearningBlog.comments.CommentDto;
import com.example.LearningBlog.comments.CommentDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentDtoMapper commentDtoMapper;
    private final PostDtoMapper postDtoMapper;

    public List<PostDto> getAllPost() {
        return postRepository.getPostsInDescOrder()
                .stream()
                .map(postDtoMapper).collect(Collectors.toList());
    }

    public PostDto getPost(Long postId) {
        return postRepository.findById(postId).map(postDtoMapper).orElseThrow();
    }


    public void addPost(Post post) {
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void updatePost(Long postId, Post post) {
        Post postToupdate = postRepository.findById(postId).orElseThrow();
        postToupdate.setTitle(post.getTitle());
        postToupdate.setPost(post.getPost());
        postRepository.save(postToupdate);


    }


    public List<CommentDto> getPostComments(Long postId) {
        List<Comment> comments = new ArrayList<>();
        Post post = postRepository.findById(postId).orElseThrow();
        comments = post.getPostComments();

        return comments.stream().map(commentDtoMapper).collect(Collectors.toList());
    }

    public void addCommentToPost(Long postId, Comment comment) {
        Post postToAddComment = postRepository.findById(postId).orElseThrow();
        postToAddComment.getPostComments().add(comment);
        comment.setPost(postToAddComment);
        postRepository.save(postToAddComment);

    }
}
