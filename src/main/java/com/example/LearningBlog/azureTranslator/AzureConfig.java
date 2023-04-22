package com.example.LearningBlog.azureTranslator;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AzureConfig {

    @Value("${azure.api.endpoint}")
    private String AzureTranslatorEndPoint;
    @Value("${azure.api.header.Ocp-Apim-Subscription-Key}")
    private String OcpApimSubscriptionKey;
    @Value("${azure.api.header.Content-type}")
    private String headerContentType;


}
