package com.example.LearningBlog.comments;


import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.blogUser.BlogUserService;
import com.example.LearningBlog.errorHandler.PostNotFoundException;
import com.example.LearningBlog.post.Post;
import com.example.LearningBlog.post.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final BlogUserService blogUserService;
    private final CommentDtoMapper commentDtoMapper;
    private final CommentRepository commentRepository;


    public void addCommentToPost(Long postId, CommentDto commentDto, Long userId) {

        Post postToAddComment = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("post not found"));
        BlogUser blogUser = blogUserService.getBlogUser(userId);
        Comment comment = commentDtoMapper.mapDtoToDomain(commentDto);
        postToAddComment.getPostComments().add(comment);

        comment.setPost(postToAddComment);
        comment.setUsername(blogUser.getUsername());
        comment.setAnonymous(false);
        comment.setBlogUser(blogUser);

        commentRepository.save(comment);
        postRepository.save(postToAddComment);
    }

    public List<Comment> getPostComments(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("post not found")).getPostComments();
    }

    public List<Comment> getComments() {
        return commentRepository.findAll();
    }
}
