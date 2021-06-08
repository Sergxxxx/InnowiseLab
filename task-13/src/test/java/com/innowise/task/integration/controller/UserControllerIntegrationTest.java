package com.innowise.task.integration.controller;

import com.innowise.task.ServingWebContentApplication;
import com.innowise.task.domain.Role;
import com.innowise.task.domain.User;
import com.innowise.task.repository.UserRepository;
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
import com.innowise.task.integration.configuration.TestConfig;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@SpringBootTest(classes = {ServingWebContentApplication.class, TestConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class
})
@ActiveProfiles("test")
public class UserControllerIntegrationTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime REGISTRATION_DATE = LocalDateTime.parse("2020-10-10 10:10", FORMATTER);
    private static final Pageable PAGEABLE = PageRequest.of(0, 20);

    private static final User USER = User.builder()
            .withId(1L)
            .withUsername("name")
            .withEmail("1@1.com")
            .withRegistrationDateTime(REGISTRATION_DATE)
            .withActive(true)
            .withRoles(Collections.singleton(Role.USER))
            .build();

    private static final User USER2 = User.builder()
            .withUsername("name")
            .withEmail("1@1.com")
            .withActive(true)
            .withRoles(Collections.singleton(Role.USER))
            .build();

    private static final Page<User> PAGE = new PageImpl<>(Collections.singletonList(USER));

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldGetUserList() throws IOException {
        String request = ReaderJsonTestUtil.getJsonFromFile("responses/expectUserRequestWithAllFields.json");

        given(userRepository.findAll(PAGEABLE)).willReturn(PAGE);

        webTestClient
                .get()
                .uri("/user")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody().json(request);

        then(userRepository).should(only()).findAll(PAGEABLE);
    }

    @Test
    void shouldUserSave() throws IOException {
        String request = ReaderJsonTestUtil.getJsonFromFile("responses/expectUserRequestWithFourFields.json");

        given(userRepository.saveAndFlush(USER2)).willReturn(USER2);

        webTestClient
                .post()
                .uri("/user")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isCreated()
                .expectBody().json(request);
    }

}