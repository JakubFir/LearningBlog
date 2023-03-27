package com.example.LearningBlog.post;


import com.example.LearningBlog.comments.Comment;
import com.example.LearningBlog.comments.CommentDto;

import com.example.LearningBlog.errorHandler.CommentNotFoundException;
import com.example.LearningBlog.errorHandler.PostNotFoundException;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/blog/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPost());
    }

    @GetMapping(path = "{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable("postId") Long postId) throws PostNotFoundException {
        return ResponseEntity.ok(postService.getPost(postId));
    }


    @PostMapping(path = "{blogUserId}")
    public void addPost(@RequestBody Post Post,@PathVariable Long blogUserId) {
        postService.addPost(Post,blogUserId);

    }

    @DeleteMapping(path = "{postId}")
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
    }


    @PutMapping(path = "{postId}")
    public void updatePost(@PathVariable("postId") Long postId, @RequestBody Post post)  {
        postService.updatePost(postId, post);
    }

    @GetMapping(path = "{postId}/comments")
    public ResponseEntity<List<CommentDto>> getPostComments(@PathVariable("postId") Long postId) {
        return ResponseEntity.ok(postService.getPostComments(postId));
    }
    @PostMapping(path = "{postId}/comments/{userId}")
    public void addCommentToPost(@PathVariable("postId") Long postId, @RequestBody Comment comment,@PathVariable("userId") Long userId) throws CommentNotFoundException {
        postService.addCommentToPost(postId, comment,userId);
    }

}
