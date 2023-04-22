package com.example.LearningBlog.kafka.config;


import com.example.LearningBlog.comments.CommentDto;


import lombok.AllArgsConstructor;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/messages")
@AllArgsConstructor
@EnableScheduling
public class MessageController {

    private final MessageService messageService;

    @PostMapping(path = "/comments/{id}")
    public void sendAnonymousCommentToKafka(@PathVariable Long id, @RequestBody CommentDto commentDto) {
        messageService.sendAnonymousCommentToKafka(id, commentDto);
    }

    @GetMapping("/comments")
    @Scheduled(cron = "*/30 * * * * *")
    public void pollAnonymousCommentsFromTopic() {
        messageService.pollAnonymousCommentsFromTopic();
      }
}