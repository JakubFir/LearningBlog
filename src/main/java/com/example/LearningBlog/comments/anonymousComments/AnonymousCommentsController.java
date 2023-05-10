package com.example.LearningBlog.comments.anonymousComments;



import com.example.LearningBlog.comments.CommentDtoMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/comments/anonymous")
@AllArgsConstructor
public class AnonymousCommentsController {

    private AnonymousCommetsService anonymousCommetsService;
    private final CommentDtoMapper commentDtoMapper;

    @PostMapping(path = "{postId}/{commentId}")
    public void addAnonymousCommentToPost(@RequestBody AnonymousCommentDto anonymousCommentDto, @PathVariable("commentId") Long commentId) {
        anonymousCommetsService.addAnonymousCommentToPost(anonymousCommentDto, commentId);
    }

    @GetMapping
    public List<AnonymousCommentDto> findAllCommentsToApprove() {
        return anonymousCommetsService.getAllCommentsToApprove().stream().map(commentDtoMapper::mapDomainToAnonymous).collect(Collectors.toList());
    }
}
