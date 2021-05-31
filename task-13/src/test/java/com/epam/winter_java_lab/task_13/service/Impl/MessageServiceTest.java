package com.epam.winter_java_lab.task_13.service.Impl;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.domain.User;
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
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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

    @Test
    public void shouldFindAllMessages() {
        given(messageRepo.findAll(any())).willReturn(PAGE);
        given(messageTransformer.transform(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.findAllMessages(PAGEABLE));

        then(messageRepo).should(only()).findAll(PAGEABLE);
        then(messageTransformer).should(only()).transform(MESSAGE);
    }

    @Test
    public void shouldSaveMessage() {
        messageService.saveMessage(MESSAGE);
        then(messageRepo).should(only()).save(MESSAGE);
    }

    @Test
    public void shouldFindMessagesByFilter() {
        given(messageRepo.findByTag("hello", PAGEABLE)).willReturn(PAGE.stream());
        given(messageTransformer.transform(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE.getContent(), messageService.findMessagesByFilter("hello", PAGEABLE));

        then(messageRepo).should(only()).findByTag("hello", PAGEABLE);
        then(messageTransformer).should(only()).transform(MESSAGE);
    }

    @Test
    public void shouldGetMessagesByCreatedDateTime() {
        given(messageRepo.getMessagesByCreatedDateTime()).willReturn(PAGE.stream());
        given(messageTransformer.transform(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE.getContent(), messageService.getMessagesByCreatedDateTime());

        then(messageRepo).should(only()).getMessagesByCreatedDateTime();
        then(messageTransformer).should(only()).transform(MESSAGE);
    }

    @Test
    public void shouldDeleteMessageById() {
        messageService.deleteMessageById(anyLong());
        then(messageRepo).should(only()).deleteById(anyLong());
    }

    @Test
    public void shouldGetSortMessagesByAsc(){
        User user = new User();
        Set<Message> messages = new HashSet<>();

        Message message1 = new Message();
        message1.setCreatedDateTime(1005L);

        Message message2 = new Message();
        message2.setCreatedDateTime(1001L);

        Message message3 = new Message();
        message3.setCreatedDateTime(1002L);

        Message message4 = new Message();
        message4.setCreatedDateTime(1003L);

        Message message5 = new Message();
        message5.setCreatedDateTime(1004L);

        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        messages.add(message4);
        messages.add(message5);

        user.setMessages(messages);
        TreeSet<Message> sortedSet = (TreeSet<Message>) messageService.getSortMessagesByAsc(user);

        assertEquals(message1, sortedSet.pollLast());
        assertEquals(message5, sortedSet.pollLast());
        assertEquals(message4, sortedSet.pollLast());
        assertEquals(message3, sortedSet.pollLast());
        assertEquals(message2, sortedSet.pollLast());
        assertEquals(0, sortedSet.size());
    }

    @Test
    public void shouldGetSortMessagesByDesc(){
        User user = new User();
        Set<Message> messages = new HashSet<>();

        Message message1 = new Message();
        message1.setCreatedDateTime(1005L);

        Message message2 = new Message();
        message2.setCreatedDateTime(1001L);

        Message message3 = new Message();
        message3.setCreatedDateTime(1002L);

        Message message4 = new Message();
        message4.setCreatedDateTime(1003L);

        Message message5 = new Message();
        message5.setCreatedDateTime(1004L);

        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        messages.add(message4);
        messages.add(message5);

        user.setMessages(messages);
        TreeSet<Message> sortedSet = (TreeSet<Message>) messageService.getSortMessagesByDesc(user);

        assertEquals(message2, sortedSet.pollLast());
        assertEquals(message3, sortedSet.pollLast());
        assertEquals(message4, sortedSet.pollLast());
        assertEquals(message5, sortedSet.pollLast());
        assertEquals(message1, sortedSet.pollLast());
        assertEquals(0, sortedSet.size());
    }
}
