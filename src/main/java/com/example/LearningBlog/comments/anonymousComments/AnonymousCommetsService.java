package com.example.LearningBlog.comments.anonymousComments;

import com.example.LearningBlog.comments.*;

import com.example.LearningBlog.post.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AnonymousCommetsService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public void addCommentToApprove(AnonymousCommentDto anonymousCommentDto) {
        Comment commentToApprove = commentMapper.mapAnonymousToDomain(anonymousCommentDto);
        commentToApprove.setAnonymousId(anonymousCommentDto.getPostId());
        commentToApprove.setAnonymous(true);
        commentRepository.save(commentToApprove);
    }

    public List<Comment> getAllCommentsToApprove() {
        return commentRepository.findAll().stream().filter(Comment::isAnonymous).collect(Collectors.toList());
    }

    public void addAnonymousCommentToPost(AnonymousCommentDto anonymousCommentDto) {
        Post post = postRepository.findById(anonymousCommentDto.getPostId()).orElseThrow();
        anonymousCommentDto.setPostId(post.getPostId());
        Comment comment = commentRepository.findById(anonymousCommentDto.getCommentId()).orElseThrow();
        comment.setUsername("Anonymous");
        comment.setPost(post);
        comment.setAnonymous(false);
        post.getPostComments().add(comment);
        commentRepository.save(comment);
    }
}
