package com.example.LearningBlog.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaTopicConfig.class);

    @Bean
    public NewTopic logs() {
        return TopicBuilder.name("logs")
                .partitions(100)
                .build();
    }

    @Bean
    public NewTopic anonymousComments() {
        return TopicBuilder.name("anonymousComments")
                .build();
    }

    @KafkaListener(topics = "anonymousComments", groupId = "groupId")
    void anonymousCommentsListener(String data) {
        System.out.println("recived an anonymous comment " + data);
    }

    @KafkaListener(topics = "logs", groupId = "groupId")
    void KafkaLogsListener(String data) {
        LOGGER.info(String.format("data reved " + data));
    }

}
