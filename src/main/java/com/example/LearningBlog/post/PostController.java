package com.example.LearningBlog.post;
import com.example.LearningBlog.errorHandler.PostNotFoundException;

import com.example.LearningBlog.kafka.config.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/blog/posts")
@AllArgsConstructor
@EnableAspectJAutoProxy
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
    public void addPost(@RequestBody PostDto postDto,@PathVariable Long blogUserId) {
        postService.addPost(postDto,blogUserId);
            }

    @DeleteMapping(path = "{postId}")
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
    }


    @PutMapping(path = "{postId}")
    public void updatePost(@PathVariable("postId") Long postId, @RequestBody PostDto postDto)  {
        postService.updatePost(postId, postDto);
    }

}
