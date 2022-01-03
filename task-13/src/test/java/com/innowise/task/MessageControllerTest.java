package com.innowise.task;

import com.innowise.task.controller.MessageController;
import com.innowise.task.service.MessageService;
import com.innowise.task.dto.MessageDto;
import com.innowise.task.dto.UserDto;
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
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {
    private static final UserDto USER_DTO = new UserDto();
    private static final MessageDto MESSAGE_DTO = new MessageDto();
    private static final Page<MessageDto> PAGE_DTO = new PageImpl<>(Collections.singletonList(MESSAGE_DTO));
    private static final Pageable PAGEABLE = Pageable.unpaged();
    private static final String TAG = "tag";

    @Mock
    private MessageService messageService;

    @InjectMocks
    private MessageController messageController;

    @Test
    public void shouldGetMessagesByCreatedDateTime()  {
        given(messageService.getMessagesByCreatedDateTime()).willReturn(PAGE_DTO);

        assertEquals(PAGE_DTO, messageController.getLatestMessages());

        then(messageService).should(only()).getMessagesByCreatedDateTime();
    }

    @Test
    public void shouldGetMessagesByFilter()  {
        given(messageService.findMessagesByFilter(TAG, PAGEABLE)).willReturn(PAGE_DTO);

        assertEquals(PAGE_DTO, messageController.getAllMessagesByTag(TAG, PAGEABLE));

        then(messageService).should(only()).findMessagesByFilter(TAG, PAGEABLE);
    }

    @Test
    public void shouldGetAllUserMessages()  {
        given(messageService.getAllMessagesByUserId(USER_DTO.getId(), PAGEABLE)).willReturn(PAGE_DTO);

        assertEquals(PAGE_DTO, messageController.userMessages(USER_DTO.getId(), PAGEABLE));

        then(messageService).should(only()).getAllMessagesByUserId(USER_DTO.getId(), PAGEABLE);
    }

    @Test
    public void shouldUpdateMessage()  {
        messageController.updateMessage(MESSAGE_DTO, MESSAGE_DTO.getId());

        then(messageService).should(only()).editMessage(MESSAGE_DTO);
    }

    @Test
    public void shouldDeleteMessage()  {
        messageController.deleteMessage(MESSAGE_DTO.getId());

        then(messageService).should(only()).deleteMessageById(MESSAGE_DTO.getId());
    }

    @Test
    public void shouldRollbackMessage()  {
        messageController.rollbackMessage(MESSAGE_DTO.getCreatedDateTime());

        then(messageService).should(only()).rollbackMessageTable(MESSAGE_DTO.getCreatedDateTime());
    }

}