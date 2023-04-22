package com.example.LearningBlog.post;

import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.blogUser.BlogUserService;


import com.example.LearningBlog.comments.CommentRepository;
import com.example.LearningBlog.errorHandler.BadCredentialsException;
import com.example.LearningBlog.errorHandler.PostNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostService {
    private final BlogUserService blogUserService;
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;
    private final PostMapper postMapper;


    public List<PostDto> getAllPost() {
        return postRepository.getPostsInDescOrder()
                .stream().map
                        (postMapper::mapPostToPostDto).collect(Collectors.toList());
    }

    public PostDto getPost(Long postId) {
        return postRepository.findById(postId)
                .map(postMapper::mapPostToPostDto)
                .orElseThrow(() -> new PostNotFoundException("not found"));
    }


    public void addPost(PostDto postDto, Long blogUserId) throws BadCredentialsException {
        Post post = postMapper.mapDtoToDomain(postDto);
        BlogUser blogUser = blogUserService.getBlogUser(blogUserId);

        blogUserService.addPostToUser(blogUserId, post);
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
