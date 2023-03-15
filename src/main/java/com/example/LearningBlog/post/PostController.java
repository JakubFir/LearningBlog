package com.example.LearningBlog.post;


import com.example.LearningBlog.comments.Comment;
import com.example.LearningBlog.comments.CommentDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/blog/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts() {
        return postService.getAllPost();
    }

    @GetMapping(path = "{postId}")
    public PostDto getPost(@PathVariable("postId") Long postId) {
        return postService.getPost(postId);
    }

    @PostMapping
    public void addPost(@RequestBody Post Post) {
        postService.addPost(Post);
    }

    @DeleteMapping(path = "{postId}")
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
    }


    @PutMapping(path = "{postId}")
    public void updatePost(@PathVariable("postId") Long postId, @RequestBody Post post) {
        postService.updatePost(postId, post);
    }

    @GetMapping(path = "{postId}/comments")
    public List<CommentDto> getPostComments(@PathVariable("postId") Long postId) {
        return postService.getPostComments(postId);
    }

    @PostMapping(path = "{postId}/comments")
    public void addCommentToPost(@PathVariable("postId") Long postId, @RequestBody Comment comment) {
        postService.addCommentToPost(postId, comment);
    }

}
