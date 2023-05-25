package com.example.LearningBlog.kafka.config;
import com.example.LearningBlog.comments.CommentRepository;
import com.example.LearningBlog.comments.anonymousComments.AnonymousCommentDto;
import com.example.LearningBlog.comments.anonymousComments.AnonymousCommetsService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final KafkaConsumer<String, String> consumer;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AnonymousCommetsService anonymousCommetsService;
    private final CommentRepository commentRepository;


    public void sendAnonymousCommentToKafka(Long id, AnonymousCommentDto anonymousCommentDto) {
        int anonymousCount = commentRepository.findAll().size()+1;
        kafkaTemplate.send("anonymousComments", id.intValue(), String.valueOf(anonymousCount), anonymousCommentDto.getCommentBody());
    }

    public void pollAnonymousCommentsFromTopic() {
        consumer.subscribe(Collections.singletonList("anonymousComments"));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            AnonymousCommentDto anonymousCommentDto = new AnonymousCommentDto(
                    Long.parseLong(record.key()),
                    (long) record.partition(),
                    record.value(),
                    "Anonymous"
            );
            anonymousCommetsService.addCommentToApprove(anonymousCommentDto);
        }
    }

    public void sendLogs(String message) {
        kafkaTemplate.send("logs", message);
    }

    public List<String> pollLogsFromTopic() {
        List<String> logs = new ArrayList<>();
        consumer.subscribe(Collections.singletonList("logs"));
        boolean keepPolling = true;
        while (keepPolling) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            if (records.isEmpty()) {
                keepPolling = false;
            } else {
                for (ConsumerRecord<String, String> record : records) {
                    logs.add(record.value());
                }
            }
        }
        return logs;
    }
}
