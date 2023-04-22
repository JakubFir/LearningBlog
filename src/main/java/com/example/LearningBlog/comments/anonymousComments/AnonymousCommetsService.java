package com.example.LearningBlog.comments.anonymousComments;

import com.example.LearningBlog.comments.*;

import com.example.LearningBlog.post.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnonymousCommetsService {

    private final CommentDtoMapper commentDtoMapper;

    private final CommentRepository commentRepository;

    private final AnonymousCommentsRepository anonymousCommentsRepository;
    private final PostRepository postRepository;

    public void add(AnonymousComments anonymousComments) {
        anonymousCommentsRepository.save(anonymousComments);
    }

    public List<AnonymousComments> getAllCommentsToApprove() {
        return anonymousCommentsRepository.findAll();
    }

    public void addAnonymousCommentToPost(Long postId, CommentDto commentDto, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow();
        Comment comment = commentDtoMapper.mapDtoToDomain(commentDto);

        comment.setUsername("Anonymous");
        comment.setPost(post);
        post.getPostComments().add(comment);
        anonymousCommentsRepository.deleteById(commentId);
        commentRepository.save(comment);

    }
}
