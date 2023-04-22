package com.example.LearningBlog.comments.anonymousComments;


import com.example.LearningBlog.comments.CommentDto;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/anonymous")
@AllArgsConstructor
public class AnonymousCommentsController {

   private AnonymousCommetsService anonymousCommetsService;


    @PostMapping
    public void addCommentToApprove(@RequestBody AnonymousComments anonymousComments){
        anonymousCommetsService.add(anonymousComments);
    }
    @PostMapping(path = "{postId}/{commentId}")
    public void addAnonymousCommentToPost(@PathVariable("postId") Long postId, @RequestBody CommentDto commentDto,@PathVariable("commentId") Long commentId){
        anonymousCommetsService.addAnonymousCommentToPost(postId,commentDto,commentId);
    }

    @GetMapping
    public List<AnonymousComments> findAllCommentsToApprove(){
        return anonymousCommetsService.getAllCommentsToApprove();
    }
}
