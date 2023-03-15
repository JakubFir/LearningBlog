package com.example.LearningBlog.mail;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/blog/contact")
public class MailController {

    MailService mailService;

    @PostMapping()
    public void sendMail(@RequestBody Mail mail){
     mailService.sendMail(mail);
    }
}
