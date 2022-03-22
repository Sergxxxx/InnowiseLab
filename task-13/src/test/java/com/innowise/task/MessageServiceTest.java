package com.innowise.task;

import com.innowise.task.domain.Message;
import com.innowise.task.dto.MessageDto;
import com.innowise.task.repository.MessageRepository;
import com.innowise.task.service.MessageService;
import com.innowise.task.transformer.MessageTransformer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
    public void shouldGetAllMessagesByUserId() {
        given(messageRepository.findMessagesByUserId(MESSAGE.getUserId() ,PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transformToDto(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.getAllMessagesByUserId(MESSAGE.getUserId() ,PAGEABLE));

        then(messageRepository).should(only()).findMessagesByUserId(MESSAGE.getUserId() ,PAGEABLE);
        then(messageTransformer).should(only()).transformToDto(MESSAGE);
    }

    @Test
    public void shouldFindMessagesByFilter() {
        given(messageRepository.findByTagLike("hello", PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transformToDto(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.findMessagesByFilter("hello", PAGEABLE));

        then(messageRepository).should(only()).findByTagLike("hello", PAGEABLE);
        then(messageTransformer).should(only()).transformToDto(MESSAGE);
    }

    @Test
    public void shouldGetMessagesByCreatedDateTime() {
        given(messageRepository.findMessageTop(PageRequest.of(0,10))).willReturn(PAGE);
        given(messageTransformer.transformToDto(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.getMessagesByCreatedDateTime());

        then(messageRepository).should(only()).findMessageTop(PageRequest.of(0,10));
        then(messageTransformer).should(only()).transformToDto(MESSAGE);
    }

    @Test
    public void shouldDeleteMessageById() {
        messageService.deleteMessageById(anyLong());
        then(messageRepository).should(only()).deleteById(anyLong());
    }

    @Test
    public void shouldSaveMessage() {
        messageService.saveMessageDto(MESSAGE_DTO);

        then(messageRepository).should(only()).saveAndFlush(messageTransformer.transformToEntity(MESSAGE_DTO));
    }

    @Test
    public void shouldRollbackMessage() {
        messageService.rollbackMessageTable(MESSAGE_DTO.getCreatedDateTime());

        then(messageRepository).should(only()).rollbackMessage(MESSAGE_DTO.getCreatedDateTime());
    }

}