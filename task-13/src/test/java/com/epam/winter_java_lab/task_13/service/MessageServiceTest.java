package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
<<<<<<< HEAD
import com.epam.winter_java_lab.task_13.repos.MessageRepository;
=======
import com.epam.winter_java_lab.task_13.repos.MessageRepo;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
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
<<<<<<< HEAD
=======
import java.util.stream.Collectors;
import java.util.stream.Stream;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
<<<<<<< HEAD
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.only;
=======
import static org.mockito.Mockito.*;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {
    private static final Message MESSAGE = new Message();
    private static final MessageDto MESSAGE_DTO = new MessageDto();
    private static final Page<MessageDto> EXPECTED_PAGE = new PageImpl<>(Collections.singletonList(MESSAGE_DTO));
    private static final Page<Message> PAGE = new PageImpl<>(Collections.singletonList(MESSAGE));
    private static final Pageable PAGEABLE = Pageable.unpaged();

    @Mock
<<<<<<< HEAD
    private MessageRepository messageRepository;
=======
    private MessageRepo messageRepo;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed

    @Mock
    private MessageTransformer messageTransformer;

    @InjectMocks
    private MessageService messageService;

<<<<<<< HEAD
    @Test
    public void shouldFindAllMessages() {
        given(messageRepository.findAll(PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transformToDto(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.findAllMessages(PAGEABLE));

        then(messageRepository).should(only()).findAll(PAGEABLE);
        then(messageTransformer).should(only()).transformToDto(MESSAGE);
    }
=======

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
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed

    @Test
    public void shouldSaveMessage() {
        messageService.saveMessage(MESSAGE_DTO);

<<<<<<< HEAD
        then(messageRepository).should(only()).save(messageTransformer.transformToEntity(MESSAGE_DTO));
=======
        then(messageRepo).should(only()).save(messageTransformer.toEntity(MESSAGE_DTO));
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

    @Test
    public void shouldFindMessagesByFilter() {
<<<<<<< HEAD
        given(messageRepository.findByTag("hello", PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transformToDto(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.findMessagesByFilter("hello", PAGEABLE));

        then(messageRepository).should(only()).findByTag("hello", PAGEABLE);
        then(messageTransformer).should(only()).transformToDto(MESSAGE);
=======
        given(messageRepo.findByTag("hello", PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transform(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.findMessagesByFilter("hello", PAGEABLE));

        then(messageRepo).should(only()).findByTag("hello", PAGEABLE);
        then(messageTransformer).should(only()).transform(MESSAGE);
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

    @Test
    public void shouldGetMessagesByCreatedDateTime() {
<<<<<<< HEAD
        given(messageRepository.findTop10ByOrderByCreatedDateTimeDesc(PAGEABLE)).willReturn(PAGE);
        given(messageTransformer.transformToDto(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(EXPECTED_PAGE, messageService.getMessagesByCreatedDateTime(PAGEABLE));

        then(messageRepository).should(only()).findTop10ByOrderByCreatedDateTimeDesc(PAGEABLE);
        then(messageTransformer).should(only()).transformToDto(MESSAGE);
=======
        given(messageRepo.getMessagesByCreatedDateTime()).willReturn(Stream.of(MESSAGE));
        given(messageTransformer.transform(MESSAGE)).willReturn(MESSAGE_DTO);

        assertEquals(Stream.of(MESSAGE_DTO).collect(Collectors.toList()), messageService.getMessagesByCreatedDateTime());

        then(messageRepo).should(only()).getMessagesByCreatedDateTime();
        then(messageTransformer).should(only()).transform(MESSAGE);
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

    @Test
    public void shouldDeleteMessageById() {
        messageService.deleteMessageById(anyLong());
<<<<<<< HEAD
        then(messageRepository).should(only()).deleteById(anyLong());
=======
        then(messageRepo).should(only()).deleteById(anyLong());
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

}
