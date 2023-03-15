package com.example.LearningBlog;

import com.example.LearningBlog.mail.Mail;
import com.example.LearningBlog.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class LearningBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningBlogApplication.class, args);
	}


}
