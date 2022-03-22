package com.innowise.task.service;

import com.innowise.task.domain.Message;
import com.innowise.task.dto.MessageDto;
import com.innowise.task.exception.NotFoundException;
import com.innowise.task.repository.MessageRepository;
import com.innowise.task.transformer.MessageTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageService {
    private static final int PAGE_NUMBER = 0;
    private static final int SIZE = 10;
    public static final PageRequest DEFAULT_PAGEABLE = PageRequest.of(PAGE_NUMBER, SIZE);
    private static final String ERROR_MESSAGE_REQUEST = "Message id %d does not exist";

    private final MessageRepository messageRepository;
    private final MessageTransformer messageTransformer;

    public MessageService(MessageRepository messageRepository, MessageTransformer messageTransformer) {
        this.messageRepository = messageRepository;
        this.messageTransformer = messageTransformer;
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> findAllMessages(Pageable pageable) {

        return messageRepository.findAll(pageable)
                .map(messageTransformer::transformToDto);
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> getAllMessagesByUserId(Long userId, Pageable pageable) {

        return messageRepository.findMessagesByUserId(userId, pageable)
                .map(messageTransformer::transformToDto);
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> findMessagesByFilter(String filter, Pageable pageable) {

        return messageRepository.findByTagLike(filter, pageable)
                .map(messageTransformer::transformToDto);
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> getMessagesByCreatedDateTime() {

        return messageRepository.findMessageTop(DEFAULT_PAGEABLE)
                .map(messageTransformer::transformToDto);
    }

    public void deleteMessageById(Long messageId) {

        messageRepository.deleteById(messageId);
    }

    public MessageDto saveMessageDto(MessageDto messageDto) {

        Message savedMessage = messageRepository.saveAndFlush(messageTransformer.transformToEntity(messageDto));

        return messageTransformer.transformToDto(savedMessage);
    }

    public void editMessage(MessageDto messageDto) {
        Long id = messageDto.getId();
        getMessageById(id).ifPresentOrElse(s-> this.saveMessageDto(messageDto),
                () -> {
                    throw new NotFoundException(String.format(ERROR_MESSAGE_REQUEST, id));
                });
    }

    private Optional<Message> getMessageById(Long id) {
        return messageRepository.findMessageById(id);
    }

    public void rollbackMessageTable(LocalDateTime desired_date) {
        messageRepository.rollbackMessage(desired_date);
    }

}