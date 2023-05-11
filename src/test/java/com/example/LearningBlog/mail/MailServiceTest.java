package com.example.LearningBlog.mail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;
    @InjectMocks
    private  MailService mailService;

    @Test
    void sendMail() {
        //Given
        Mail mail = new Mail("asd","asd","asd");
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mail.getTitle());
        mailMessage.setTo("jakubfirlejczyk@gmail.com");
        mailMessage.setText(mail.getMessage());
        mailMessage.setSubject(mail.getName());

        //When
        mailService.sendMail(mail);

        //Then
        verify(javaMailSender,  times(1)).send(mailMessage);
    }
}