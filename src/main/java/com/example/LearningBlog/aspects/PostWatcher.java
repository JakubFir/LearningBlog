package com.example.LearningBlog.aspects;

import com.example.LearningBlog.kafka.config.MessageService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PostWatcher {

    private final MessageService messageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogUserWatcher.class);

    @Before("execution(public * com.example.LearningBlog.post.PostController.addPost(..))")
    public void BeforeAddingPostLog() {
        messageService.sendLogs("adding post");
        LOGGER.info("adding post");
    }

    @After("execution(public * com.example.LearningBlog.post.PostController.addPost(..))")
    public void AfterAddingPostLog() {
        messageService.sendLogs("post added");
        LOGGER.info("post added");
    }


    @Before("execution(public * com.example.LearningBlog.post.PostController.deletePost(..))")
    public void BeforeDeletingPostLog() {
        messageService.sendLogs("deleting post");
        LOGGER.info("deleting post");
    }

    @After("execution(public * com.example.LearningBlog.post.PostController.deletePost(..))")
    public void AfterDeletingPostLog() {
        messageService.sendLogs("post deleted");
        LOGGER.info("post deleted");
    }
}
