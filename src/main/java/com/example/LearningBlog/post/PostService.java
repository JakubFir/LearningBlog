package com.example.LearningBlog.post;

import com.example.LearningBlog.blogUser.BlogUser;
import com.example.LearningBlog.blogUser.BlogUserService;
import com.example.LearningBlog.comments.Comment;
import com.example.LearningBlog.comments.CommentDto;
import com.example.LearningBlog.comments.CommentDtoMapper;
import com.example.LearningBlog.errorHandler.BadCredincialsException;
import com.example.LearningBlog.errorHandler.CommentNotFoundException;
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
    private final CommentDtoMapper commentDtoMapper;
    private final PostDtoMapper postDtoMapper;

    public List<PostDto> getAllPost() {
        return postRepository.getPostsInDescOrder()
                .stream().map
                        (postDtoMapper::mapPostToPostDto).collect(Collectors.toList());
    }

    public PostDto getPost(Long postId)   {
        return postRepository.findById(postId).map(postDtoMapper::mapPostToPostDto).orElseThrow(() -> new PostNotFoundException("not found"));
    }


    public void addPost(Post post, Long blogUserId) throws BadCredincialsException {
        blogUserService.savePost(blogUserId, post);
        post.setBlogUser(blogUserService.getBlogUser(blogUserId));
        postRepository.save(post);
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public void updatePost(Long postId, Post post) {
        Post postToUpdate = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("post not found"));
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setPost(post.getPost());
        postRepository.save(postToUpdate);


    }


    public List<CommentDto> getPostComments(Long postId)  {
        List<Comment> comments;
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("post not found"));
        comments = post.getPostComments();
        return comments.stream().map(commentDtoMapper::mapCommentToCommentDto).collect(Collectors.toList());
    }

    public void addCommentToPost(Long postId, Comment comment, Long userId) throws CommentNotFoundException {
        Post postToAddComment = postRepository.findById(postId).orElseThrow(CommentNotFoundException::new);
        BlogUser blogUser = blogUserService.getBlogUser(userId);
        postToAddComment.getPostComments().add(comment);
        comment.setPost(postToAddComment);
        comment.setUsername(blogUser.getUsername());
        postRepository.save(postToAddComment);

    }
}
