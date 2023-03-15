package com.example.LearningBlog.post;

import com.example.LearningBlog.comments.Comment;
import com.example.LearningBlog.comments.CommentDto;
import com.example.LearningBlog.comments.CommentDtoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    private PostService underTest;
    private  final CommentDtoMapper commentDtoMapper = new CommentDtoMapper();
    private final PostDtoMapper postDtoMapper = new PostDtoMapper();
    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        underTest = new PostService(postRepository,commentDtoMapper,postDtoMapper);
    }

    @Test
    void getAllPost() {
        //when
        underTest.getAllPost();
        //then
        verify(postRepository).getPostsInDescOrder();
    }

    @Test
    void addPost() {
        //given
        Post post= new Post();
        post.setPostId(1L);
        post.setTitle("Test Title");
        post.setPost("Test Post");
        //when
        underTest.addPost(post);
        //then
        ArgumentCaptor<Post> postDtoArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postDtoArgumentCaptor.capture());
        Post capturedPost = postDtoArgumentCaptor.getValue();

        assertEquals(post.getPostId(), capturedPost.getPostId());
        assertEquals(post.getTitle(), capturedPost.getTitle());
        assertEquals(post.getPost(), capturedPost.getPost());

    }

    @Test
    void deletePost() {
        //given
        long id = 10;

        //when
        underTest.deletePost(id);
        //then
        verify(postRepository).deleteById(id);
    }

    @Test
    void updatePost() {
        //given
        Post post = new Post();
        post.setTitle("asd");
        post.setPostId(2L);
        post.setPost("asd");
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));

        Post post2= new Post();
        post2.setTitle("assd");
        post2.setPost("deee");
        post2.setPostId(2L);

        //when
        underTest.updatePost(2L, post2);
        ArgumentCaptor<Post> postDtoArgumentCaptor = ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postDtoArgumentCaptor.capture());
        Post capturedPost = postDtoArgumentCaptor.getValue();

        //then
        assertEquals(post.getPostId(), capturedPost.getPostId());
        assertEquals(post.getTitle(), capturedPost.getTitle());
        assertEquals(post.getPost(), capturedPost.getPost());

    }

    @Test
    void getPost() {
        //given
        Post post = new Post();
        post.setTitle("asd");
        post.setPostId(2L);
        post.setPost("asd");

        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        PostDto expected = postDtoMapper.apply(post);
        //when
      PostDto result = underTest.getPost(post.getPostId());

        //then
        assertEquals(expected,result);


    }

    @Test
    public void testGetPostComments() {
        //given
        Post post = new Post();
        List<Comment> comments = new ArrayList<>();
        post.setPostId(1L);
        post.setPost("This is a test post.");
        post.setTitle("Test Post");
        post.setPostComments(comments);

        Comment comment1 = new Comment();
        comment1.setUsername("User1");
        comment1.setCommentBody("This is comment 1.");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setUsername("User2");
        comment2.setCommentBody("This is comment 2.");
        comment2.setPost(post);

        post.getPostComments().add(comment1);
        post.getPostComments().add(comment2);
        postRepository.save(post);

        //when
        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        List<CommentDto> result = underTest.getPostComments(1L);
        //then
        assertEquals(2, result.size());
    }

    @Test
    void addCommentToPost() {
        //given
        Post post = new Post();
        List<Comment> comments = new ArrayList<>();
        post.setPostId(1L);
        post.setPost("This is a test post.");
        post.setTitle("Test Post");
        post.setPostComments(comments);

        Comment comment1 = new Comment();
        comment1.setUsername("User1");
        comment1.setCommentBody("This is comment 1.");

        when(postRepository.findById(post.getPostId())).thenReturn(Optional.of(post));
        underTest.addCommentToPost(1L, comment1);

        assertEquals(1, post.getPostComments().size());

    }
}