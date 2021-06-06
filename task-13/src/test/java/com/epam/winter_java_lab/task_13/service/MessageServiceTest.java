package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
import com.epam.winter_java_lab.task_13.repos.MessageRepo;
import com.epam.winter_java_lab.task_13.transformer.MessageTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    private static final Message MESSAGE = new Message();
    private static final MessageDto MESSAGE_DTO = new MessageDto();
    private static final Page<MessageDto> EXPECTED_PAGE = new PageImpl<>(Collections.singletonList(MESSAGE_DTO));
    private static final Page<Message> PAGE = new PageImpl<>(Collections.singletonList(MESSAGE));
    private static final Pageable PAGEABLE = Pageable.unpaged();

    @Mock
    private MessageRepo messageRepo;

    @Mock
    private MessageTransformer messageTransformer;

    @InjectMocks
    private MessageService messageService;


//    @Test
//    public void shouldFindAllMessages() {
//        given(messageRepo.findAll(any())).willReturn(PAGE);
//        given(messageTransformer.transform(MESSAGE)).willReturn(MESSAGE_DTO);
//
//        assertEquals(EXPECTED_PAGE, messageService.findAllMessages(PAGEABLE));
//
//        then(messageRepo).should(only()).findAll(PAGEABLE);
//        then(messageTransformer).should(only()).transform(MESSAGE);
//    }

    @Test
    public void shouldSaveMessage() {
        messageService.saveMessage(MESSAGE_DTO);

        then(messageRepo).should(only()).save(messageTransformer.toEntity(MESSAGE_DTO));
    }

    @Test
    public void shouldFindMessagesByFilter() {
        given(messageRepo.findByTag("hello", PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transform(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.findMessagesByFilter("hello", PAGEABLE));

        then(messageRepo).should(only()).findByTag("hello", PAGEABLE);
        then(messageTransformer).should(only()).transform(MESSAGE);
    }

    @Test
    public void shouldGetMessagesByCreatedDateTime() {
        given(messageRepo.getMessagesByCreatedDateTime()).willReturn(Stream.of(MESSAGE));
        given(messageTransformer.transform(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(Stream.of(MESSAGE_DTO).collect(Collectors.toList()), messageService.getMessagesByCreatedDateTime());

        then(messageRepo).should(only()).getMessagesByCreatedDateTime();
        then(messageTransformer).should(only()).transform(MESSAGE);
    }

    @Test
    public void shouldDeleteMessageById() {
        messageService.deleteMessageById(anyLong());
        then(messageRepo).should(only()).deleteById(anyLong());
    }

}
