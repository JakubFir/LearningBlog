package com.example.LearningBlog.comments.anonymousComments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnonymousCommentsRepository extends JpaRepository<AnonymousComments,Long> {
}
