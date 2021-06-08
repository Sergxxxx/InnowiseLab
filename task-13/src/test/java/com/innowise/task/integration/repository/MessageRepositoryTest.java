package com.innowise.task.integration.repository;

import com.innowise.task.domain.Message;
import com.innowise.task.extention.PostgreSQLTestContainer;
import com.innowise.task.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@DataJpaTest
@SqlGroup({
        @Sql("/sql/dataUser.sql"),
        @Sql("/sql/dataMessage.sql")
})
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test", "enable-jpa-audition"})
@PostgreSQLTestContainer
@DirtiesContext
public class MessageRepositoryTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final LocalDateTime CREATED_DATE_ONE = LocalDateTime.parse("2020-10-10 10:10", FORMATTER);
    private static final LocalDateTime UPDATED_DATE_ONE = LocalDateTime.parse("2020-10-10 10:20", FORMATTER);

    private static final LocalDateTime CREATED_DATE_TWO = LocalDateTime.parse("2020-10-10 10:30", FORMATTER);
    private static final LocalDateTime UPDATED_DATE_TWO = LocalDateTime.parse("2020-10-10 10:40", FORMATTER);

    private static final Pageable PAGEABLE = PageRequest.of(0, 20);

    private static final Message MESSAGE_ONE = Message.builder()
            .withId(1L)
            .withText("one")
            .withTag("one")
            .withCreatedDateTime(CREATED_DATE_ONE)
            .withUpdatedDateTime(UPDATED_DATE_ONE)
            .withUserId(1L)
            .build();

    private static final Message MESSAGE_TWO = Message.builder()
            .withId(2L)
            .withText("two")
            .withTag("two")
            .withCreatedDateTime(CREATED_DATE_TWO)
            .withUpdatedDateTime(UPDATED_DATE_TWO)
            .withUserId(2L)
            .build();

    @Autowired
    private MessageRepository messageRepository;

    @ParameterizedTest
    @MethodSource("findByTagLikeDataProvider")
    void shouldFindByTagLike(int expected, String tag) {
        assertEquals(expected, messageRepository.findByTagLike(tag, PAGEABLE).getTotalElements());
    }

    @Test
    void shouldFindByTagLike() {
       assertEquals(Optional.of(MESSAGE_ONE), messageRepository.findByTagLike("one", PAGEABLE).get().findFirst());
    }

    @Test
    void shouldFindMessageTop() {
        assertEquals(Optional.of(MESSAGE_TWO), messageRepository.findMessageTop(PAGEABLE).get().findFirst());
        assertEquals(2, messageRepository.findMessageTop(PAGEABLE).getTotalElements());
        assertEquals(1, messageRepository.findMessageTop(PAGEABLE).getTotalPages());
    }

    @Test
    void shouldFindAllMessages() {
        assertEquals(Optional.of(MESSAGE_ONE), messageRepository.findAll(PAGEABLE).get().findFirst());
        assertEquals(2, messageRepository.findAll(PAGEABLE).getTotalElements());
        assertEquals(1, messageRepository.findAll(PAGEABLE).getTotalPages());
    }

    @Test
    void shouldFindMessagesByUserId() {
        assertEquals(1, messageRepository.findMessagesByUserId(1L, PAGEABLE).getTotalElements());
        assertEquals(1, messageRepository.findMessagesByUserId(2L, PAGEABLE).getTotalElements());
        assertEquals(1, messageRepository.findMessagesByUserId(1L, PAGEABLE).getTotalPages());
        assertEquals(Optional.of(MESSAGE_ONE), messageRepository.findMessagesByUserId(1L, PAGEABLE).get().findFirst());
        assertEquals(Optional.of(MESSAGE_TWO), messageRepository.findMessagesByUserId(2L, PAGEABLE).get().findFirst());
    }

    @Test
    void shouldFindById() {
        assertEquals(Optional.of(MESSAGE_ONE), messageRepository.findMessageById(1L));
        assertEquals(Optional.of(MESSAGE_TWO), messageRepository.findMessageById(2L));
    }

    @Test
    void shouldRollbackMessage() {
        assertEquals(2, messageRepository.findAll(Pageable.unpaged()).getTotalElements());

        messageRepository.rollbackMessage(CREATED_DATE_ONE);

        assertEquals(0, messageRepository.findAll(Pageable.unpaged()).getTotalElements());
    }

    private static Stream<Arguments> findByTagLikeDataProvider() {
        return Stream.of(
                arguments(1, "one"),
                arguments(1, "two"),
                arguments(2, "o"),
                arguments(1, "t"),
                arguments(0, "k")
        );
    }

}