package com.example.LearningBlog.blogUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface BlogUserRepository extends JpaRepository<BlogUser,Long> {

    Optional<BlogUser> findBlogUsersByUsername(String username);
}
