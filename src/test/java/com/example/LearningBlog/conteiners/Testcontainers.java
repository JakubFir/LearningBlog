package com.example.LearningBlog.conteiners;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@org.testcontainers.junit.jupiter.Testcontainers
public class Testcontainers {

    protected static Network sharedNetwork = Network.newNetwork();


    @Container
    protected static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.1")
            .withDatabaseName("blogdatabasetests")
            .withPassword("password")
            .withUsername("postgres")
            .withExposedPorts(5432)
            .withNetwork(sharedNetwork);

    @DynamicPropertySource
    public static void postgresContainerConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Container
    protected static KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.2"))
            .withNetwork(postgreSQLContainer.getNetwork())
            .withNetworkAliases("kafka");

    @DynamicPropertySource
    public static void kafkaConfig(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @Container
    protected static GenericContainer<?> backendContainer = new GenericContainer<>(DockerImageName.parse("learningblog-backend"))
            .withExposedPorts(8080)
            .withNetwork(postgreSQLContainer.getNetwork())
            .withNetworkAliases("backend")
            .dependsOn(postgreSQLContainer, kafkaContainer)
            .withEnv("spring.datasource.url", "jdbc:postgresql://" + postgreSQLContainer.getNetworkAliases().iterator().next() + ":" + "5432/blogdatabasetests")
            .withEnv("spring.kafka.bootstrap-servers", kafkaContainer.getNetworkAliases().iterator().next() + ":9092")
            .withEnv("spring.datasource.username", "postgres")
            .withEnv("spring.datasource.password", "password")
            .withEnv("server.port", "8080");
}

