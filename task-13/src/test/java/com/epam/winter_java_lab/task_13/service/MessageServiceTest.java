package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
import com.epam.winter_java_lab.task_13.repos.MessageRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    private static final Message MESSAGE = new Message();
    private static final MessageDto MESSAGE_DTO = new MessageDto();
    private static final Page<MessageDto> EXPECTED_PAGE = new PageImpl<>(Collections.singletonList(MESSAGE_DTO));
    private static final Page<Message> PAGE = new PageImpl<>(Collections.singletonList(MESSAGE));
    private static final Pageable PAGEABLE = Pageable.unpaged();

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageTransformer messageTransformer;

    @InjectMocks
    private MessageService messageService;

    @Test
    public void shouldFindAllMessages() {
        given(messageRepository.findAll(PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transformToDto(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.findAllMessages(PAGEABLE));

        then(messageRepository).should(only()).findAll(PAGEABLE);
        then(messageTransformer).should(only()).transformToDto(MESSAGE);
    }

    @Test
    public void shouldSaveMessage() {
        messageService.saveMessage(MESSAGE_DTO);

        then(messageRepository).should(only()).save(messageTransformer.transformToEntity(MESSAGE_DTO));
    }

    @Test
    public void shouldFindMessagesByFilter() {
        given(messageRepository.findByTag("hello", PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transformToDto(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.findMessagesByFilter("hello", PAGEABLE));

        then(messageRepository).should(only()).findByTag("hello", PAGEABLE);
        then(messageTransformer).should(only()).transformToDto(MESSAGE);
    }

    @Test
    public void shouldGetMessagesByCreatedDateTime() {
        given(messageRepository.findTop10ByOrderByCreatedDateTimeDesc(PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transformToDto(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.getMessagesByCreatedDateTime(PAGEABLE));

        then(messageRepository).should(only()).findTop10ByOrderByCreatedDateTimeDesc(PAGEABLE);
        then(messageTransformer).should(only()).transformToDto(MESSAGE);
    }

    @Test
    public void shouldDeleteMessageById() {
        messageService.deleteMessageById(anyLong());
        then(messageRepository).should(only()).deleteById(anyLong());
    }

}
