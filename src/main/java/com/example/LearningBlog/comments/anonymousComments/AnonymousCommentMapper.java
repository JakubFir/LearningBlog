package com.example.LearningBlog.comments.anonymousComments;

import com.example.LearningBlog.comments.Comment;
import org.springframework.stereotype.Service;

@Service
public class AnonymousCommentMapper {

    public Comment mapToComment(AnonymousComments anonymousComments) {
        return new Comment(anonymousComments.getCommentId(),
                anonymousComments.getUsername(),
                anonymousComments.getCommentBody(),
                anonymousComments.getDateOfPublishing());
    }
}
