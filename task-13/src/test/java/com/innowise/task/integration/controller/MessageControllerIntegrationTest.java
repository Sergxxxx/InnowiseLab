package com.innowise.task.integration.controller;

import com.innowise.task.ServingWebContentApplication;
import com.innowise.task.domain.Message;
import com.innowise.task.integration.configuration.TestConfig;
import com.innowise.task.repository.MessageRepository;
import com.innowise.task.service.MessageService;
import com.innowise.task.util.ReaderJsonTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Optional;

import static com.innowise.task.controller.MessageController.MESSAGES_BASE_URL;
import static com.innowise.task.controller.MessageController.MESSAGE_BASE_URL;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;

@SpringBootTest(classes = {ServingWebContentApplication.class, TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class
})
@ActiveProfiles("test")
public class MessageControllerIntegrationTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime CREATED_DATE = LocalDateTime.parse("2020-10-10 10:10", FORMATTER);
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2020-10-10 10:20", FORMATTER);
    private static final Pageable PAGEABLE = PageRequest.of(0, 20);

    private static final Message MESSAGE_WITH_ALL_FIELDS = Message.builder()
            .withId(1L)
            .withText("text")
            .withTag("tag")
            .withCreatedDateTime(CREATED_DATE)
            .withUpdatedDateTime(UPDATED_DATE)
            .withUserId(1L)
            .build();
    private static final Page<Message> PAGE_MESSAGE_WITH_ALL_FIELDS = new PageImpl<>(Collections.singletonList(MESSAGE_WITH_ALL_FIELDS));

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private MessageRepository messageRepository;

    @Test
    void shouldGetMessagesByCreatedDateTime() throws IOException {
        String request = ReaderJsonTestUtil.getJsonFromFile("responses/expectRequestWithAllFields.json");

        given(messageRepository.findMessageTop(MessageService.DEFAULT_PAGEABLE)).willReturn(PAGE_MESSAGE_WITH_ALL_FIELDS);

        webTestClient
                .get()
                .uri(MESSAGES_BASE_URL)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().json(request);

        then(messageRepository).should(only()).findMessageTop(MessageService.DEFAULT_PAGEABLE);
    }

    @Test
    void shouldGetMessagesByFilter() throws IOException {
        String request = ReaderJsonTestUtil.getJsonFromFile("responses/expectRequestWithAllFields.json");

        given(messageRepository.findByTagLike("tag", PAGEABLE)).willReturn(PAGE_MESSAGE_WITH_ALL_FIELDS);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(MESSAGES_BASE_URL + "/filter")
                        .queryParam("tag", "tag")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().json(request);

        then(messageRepository).should(only()).findByTagLike("tag", PAGEABLE);
    }

    @Test
    void shouldGetUserMessages() throws IOException {
        String request = ReaderJsonTestUtil.getJsonFromFile("responses/expectRequestWithAllFields.json");

        given(messageRepository.findMessagesByUserId(1L, PAGEABLE)).willReturn(PAGE_MESSAGE_WITH_ALL_FIELDS);

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(MESSAGES_BASE_URL + "/userId")
                        .queryParam("userId", 1)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().json(request);

        then(messageRepository).should(only()).findMessagesByUserId(1L, PAGEABLE);
    }

    @Test
    void shouldSaveMessage() throws IOException {
        String request = ReaderJsonTestUtil.getJsonFromFile("responses/expectRequestWithThreeFields.json");

        Message message = Message.builder()
                .withText("text")
                .withTag("tag")
                .withUserId(1L)
                .build();

        given(messageRepository.saveAndFlush(message)).willReturn(message);

        webTestClient
                .post()
                .uri(MESSAGE_BASE_URL + "/add")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated()
                .expectBody().json(request);

        then(messageRepository).should(only()).saveAndFlush(message);
    }

    @Test
    void shouldUpdateMessage() throws IOException {
        String request = ReaderJsonTestUtil.getJsonFromFile("responses/expectRequestWithFourFields.json");

        Message message = Message.builder()
                .withId(1L)
                .withText("text")
                .withTag("tag")
                .withUserId(1L)
                .build();

        given(messageRepository.findMessageById(message.getId())).willReturn(Optional.of(message));
        given(messageRepository.saveAndFlush(message)).willReturn(message);

        webTestClient
                .put()
                .uri(uriBuilder -> uriBuilder
                        .path(MESSAGE_BASE_URL + "/update/1")
                        .build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().is2xxSuccessful();

        then(messageRepository).should(times(1)).findMessageById(message.getId());
        then(messageRepository).should(times(1)).saveAndFlush(message);
        then(messageRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void shouldDeleteMessage() {

        webTestClient
                .delete()
                .uri(MESSAGE_BASE_URL + "/delete/1")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk();

        then(messageRepository).should(only()).deleteById(1L);
    }

    @Test
    void shouldRollbackInsertMessage() {
        given(messageRepository.saveAndFlush(MESSAGE_WITH_ALL_FIELDS)).willReturn(MESSAGE_WITH_ALL_FIELDS);

        webTestClient
                .post()
                .uri(MESSAGE_BASE_URL + "/rollback")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(CREATED_DATE))
                .exchange()
                .expectStatus().isOk();

        assertTrue(messageRepository.findAll().isEmpty());
    }

}