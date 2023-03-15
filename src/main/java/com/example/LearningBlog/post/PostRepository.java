package com.example.LearningBlog.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("FROM Post p ORDER BY p.postId DESC")
    List<Post> getPostsInDescOrder();
}
