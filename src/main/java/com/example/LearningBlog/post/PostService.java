package com.example.LearningBlog.post;

import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.blogUser.BlogUserService;


import com.example.LearningBlog.errorHandler.BadCredentialsException;
import com.example.LearningBlog.errorHandler.PostNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final BlogUserService blogUserService;
    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public List<Post> getAllPost() {
        return postRepository.getPostsInDescOrder();
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElseThrow();
    }


    public void addPost(PostDto postDto, Long blogUserId) throws BadCredentialsException {
        BlogUser blogUser = blogUserService.getBlogUser(blogUserId);
        Post post = postMapper.mapDtoToDomain(postDto);
        blogUser.getUserPosts().add(post);
        post.setBlogUser(blogUser);
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void updatePost(Long postId, PostDto postDto) {
        Post post = postMapper.mapDtoToDomain(postDto);

        Post postToUpdate = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("post not found"));
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setPost(post.getPost());
        postRepository.save(postToUpdate);
    }

}
