package com.example.LearningBlog.conteiners;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContainersTest extends Testcontainers {
    @Test
    void canStartPostgresDB() {
        assertThat(postgreSQLContainer.isRunning()).isTrue();
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(kafkaContainer.isRunning()).isTrue();
        assertThat(kafkaContainer.isCreated()).isTrue();
    }
}
