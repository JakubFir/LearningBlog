package com.example.LearningBlog.azureTranslator;


import com.example.LearningBlog.errorHandler.ServiceUnavailableException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;


@RequiredArgsConstructor
@Service
public class AzureClient {
    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    private final AzureConfig azureConfig;

    public String translate(TranslationDto text) throws IOException {

        HttpHeaders httpHeaders = new HttpHeaders();
        String responseEntityBody = "";
        httpHeaders.setAccept(Collections.singletonList(org.springframework.http.MediaType.APPLICATION_JSON));
        httpHeaders.add("Ocp-Apim-Subscription-Key", azureConfig.getOcpApimSubscriptionKey());
        httpHeaders.add("Content-type", azureConfig.getHeaderContentType());

        String jsonTranslationBody = objectMapper.writeValueAsString(Collections.singletonList(text));

        HttpEntity<String> entity = new HttpEntity<>(jsonTranslationBody, httpHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(azureConfig.getAzureTranslatorEndPoint(), HttpMethod.POST, entity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            responseEntityBody = responseEntity.getBody();
            return getTranslatedTextFromResponse(responseEntityBody);
        } else
            throw new ServiceUnavailableException("Service currently unavailable");
    }

    private String getTranslatedTextFromResponse(String responseEntityBody) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode rootNode = objectMapper.readTree(responseEntityBody);

        JsonNode elementNode = rootNode.get(0);
        JsonNode translationsNode = elementNode.get("translations");
        JsonNode translationNode = translationsNode.get(0);

        return translationNode.get("text").asText();
    }

}

