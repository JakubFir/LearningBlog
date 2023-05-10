package com.example.LearningBlog.kafka.config;




import com.example.LearningBlog.comments.anonymousComments.AnonymousCommentDto;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/messages")
@AllArgsConstructor
@EnableScheduling
public class MessageController {
    private final MessageService messageService;

    @PostMapping(path = "/comments/{id}")
    public void sendAnonymousCommentToKafka(@PathVariable Long id, @RequestBody AnonymousCommentDto anonymousCommentDto) {
        messageService.sendAnonymousCommentToKafka(id, anonymousCommentDto);
    }

    @GetMapping("/comments")
    @Scheduled(cron = "*/30 * * * * *")
    public void pollAnonymousCommentsFromTopic() {
        messageService.pollAnonymousCommentsFromTopic();
    }

    @PostMapping(path = "/logs")
    public void sendLogsToKafka(String message) {
        messageService.sendLogs(message);
    }

    @GetMapping(path = "/logs")
    public List<String> pollLogsFromTopic() {
        return messageService.pollLogsFromTopic();
    }
}