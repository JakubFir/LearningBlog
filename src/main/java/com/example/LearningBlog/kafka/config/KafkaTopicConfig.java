package com.example.LearningBlog.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaTopicConfig {

    @Bean
   public NewTopic originalPosts (){
        return TopicBuilder.name("originalPosts")
                .build();
   }
   @Bean
   public NewTopic anonymousComments (){
        return TopicBuilder.name("anonymousComments")
                .build();
   }

   @KafkaListener(topics = "anonymousComments", groupId = "groupId")
   void anonymousCommentsListener(String data) {
       System.out.println("recived an anonymous comment " + data);
   }

   @KafkaListener(topics = "originalPosts", groupId = "groupId")
   void kafkaOriginalPostListener(String data){
       System.out.println("recived data "  +data);
   }

}
