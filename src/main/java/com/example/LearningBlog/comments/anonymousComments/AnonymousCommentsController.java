package com.example.LearningBlog.comments.anonymousComments;



import com.example.LearningBlog.comments.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/comments/anonymous")
@AllArgsConstructor
public class AnonymousCommentsController {

    private AnonymousCommetsService anonymousCommetsService;
    private final CommentMapper commentMapper;

    @PostMapping(path = "{postId}")
    public void addAnonymousCommentToPost(@RequestBody AnonymousCommentDto anonymousCommentDto) {
        anonymousCommetsService.addAnonymousCommentToPost(anonymousCommentDto);
    }

    @GetMapping
    public List<AnonymousCommentDto> findAllCommentsToApprove() {
        return anonymousCommetsService.getAllCommentsToApprove().stream().map(commentMapper::mapDomainToAnonymous).collect(Collectors.toList());
    }
}
