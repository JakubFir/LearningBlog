package com.example.LearningBlog.azureTranslator;



import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;


@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public HttpHeaders httpHeaders(){
        return new HttpHeaders();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
