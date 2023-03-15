package com.example.LearningBlog.mail;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public void sendMail(Mail mail) {
        SimpleMailMessage massage = new SimpleMailMessage();
        massage.setFrom(mail.getTitle());
        massage.setTo("jakubfirlejczyk@gmail.com");
        massage.setText(mail.getMessage());
        massage.setSubject(mail.getName());

        javaMailSender.send(massage);
    }
}
