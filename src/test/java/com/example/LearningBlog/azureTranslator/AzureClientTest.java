package com.example.LearningBlog.azureTranslator;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;





@ExtendWith(MockitoExtension.class)
class AzureClientTest {
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private HttpHeaders httpHeaders;
    @Mock
    private AzureConfig azureConfig;

    private AzureClient azureClient;

    @BeforeEach
    void setUp() {
        azureClient = new AzureClient(restTemplate,httpHeaders,azureConfig);
    }
}