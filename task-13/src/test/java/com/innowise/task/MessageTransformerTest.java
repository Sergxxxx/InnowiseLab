package com.innowise.task;

import com.innowise.task.domain.Message;
import com.innowise.task.dto.MessageDto;
import com.innowise.task.transformer.MessageTransformer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageTransformerTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime CREATED_DATE = LocalDateTime.parse("2020-10-10 10:10", FORMATTER);
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.parse("2020-10-10 10:20", FORMATTER);

    private final MessageTransformer messageTransformer = new MessageTransformer();

    private static final Message MESSAGE = Message.builder()
        .withId(1L)
        .withText("text")
        .withTag("tag")
        .withCreatedDateTime(CREATED_DATE)
        .withUpdatedDateTime(UPDATED_DATE)
        .withUserId(1L)
        .build();

    private static final MessageDto MESSAGE_DTO = MessageDto.builder()
        .withId(1L)
        .withText("text")
        .withTag("tag")
        .withCreatedDateTime(CREATED_DATE)
        .withUpdatedDateTime(UPDATED_DATE)
        .withUserId(1L)
        .build();

    @Test
    public void shouldTransformToDto()  {
        assertEquals(MESSAGE_DTO, messageTransformer.transformToDto(MESSAGE));
    }

    @Test
    public void shouldTransformToEntity()  {
        assertEquals(MESSAGE, messageTransformer.transformToEntity(MESSAGE_DTO));
    }

}