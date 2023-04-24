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
public class BlogUserWatcher {
    private final MessageService messageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BlogUserWatcher.class);

    @Before("execution(public * com.example.LearningBlog.blogUser.BlogUserController.updateBlogUserRole(..))")
    public void BeforeUpdateRoleLog() {
        messageService.sendLogs("editing user role");
        LOGGER.info("editing user role");
    }

    @After("execution(public * com.example.LearningBlog.blogUser.BlogUserController.updateBlogUserRole(..))")
    public void AfterUpdateRoleLog() {
        messageService.sendLogs("user role edited");
        LOGGER.info("user role edited");
    }

    @Before("execution(public * com.example.LearningBlog.blogUser.BlogUserController.addAdmin(..))")
    public void BeforeAddingAdminLog() {
        messageService.sendLogs("adding admin");
        LOGGER.info("adding admin");
    }

    @After("execution(public * com.example.LearningBlog.blogUser.BlogUserController.addAdmin(..))")
    public void AfterAddingAdminLog() {
        messageService.sendLogs("admin added");
        LOGGER.info("admin added");
    }

    @Before("execution(public * com.example.LearningBlog.blogUser.BlogUserController.addUser(..))")
    public void BeforeAddingUserLog() {
        messageService.sendLogs("adding user");
        LOGGER.info("adding user");
    }

    @After("execution(public * com.example.LearningBlog.blogUser.BlogUserController.addUser(..))")
    public void AfterAddingUserLog() {
        messageService.sendLogs("user added");
        LOGGER.info("user added");
    }
}
