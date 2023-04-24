package com.example.LearningBlog.kafka.config;

import com.example.LearningBlog.comments.CommentDto;
import com.example.LearningBlog.comments.anonymousComments.AnonymousComments;
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
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final KafkaConsumer<String, String> consumer;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AnonymousCommetsService anonymousCommetsService;

    public void sendAnonymousCommentToKafka(Long id, CommentDto commentDto) {
        kafkaTemplate.send("anonymousComments", id.toString(), commentDto.getCommentBody());
    }

    public void pollAnonymousCommentsFromTopic() {
        consumer.subscribe(Collections.singletonList("anonymousComments"));
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        for (ConsumerRecord<String, String> record : records) {
            AnonymousComments comments = new AnonymousComments(
                    Long.parseLong(record.key()),
                    record.value(),
                    "Anonymous",
                    new Date()
            );
            anonymousCommetsService.add(comments);
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
