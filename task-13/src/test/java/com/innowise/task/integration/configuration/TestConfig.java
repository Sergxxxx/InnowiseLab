package com.innowise.task.integration.configuration;

import com.innowise.task.repository.MessageRepository;
import com.innowise.task.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public MessageRepository messageRepository() {
        return Mockito.mock(MessageRepository.class);
    }

    @Bean
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

}