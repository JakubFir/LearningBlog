package com.example.LearningBlog.comments;

import com.example.LearningBlog.errorHandler.CommentNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("api/v1/comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;
    @PostMapping(path = "{postId}/{userId}")
    public void addCommentToPost(@PathVariable("postId") Long postId, @RequestBody CommentDto commentDto, @PathVariable("userId") Long userId) throws CommentNotFoundException {
        commentService.addCommentToPost(postId, commentDto, userId);
    }
    @GetMapping
    public List<CommentDto> getComments() {
        return commentService.getComments().stream().map(commentMapper::mapCommentToCommentDto).collect(Collectors.toList());
    }
    @GetMapping(path = "{postId}")
    public ResponseEntity<List<CommentDto>> getPostComments(@PathVariable("postId") Long postId) {
        List<Comment> postComments = commentService.getPostComments(postId);
        return ResponseEntity.ok(postComments.stream().map(commentMapper::mapCommentToCommentDto).collect(Collectors.toList()));
    }
}
