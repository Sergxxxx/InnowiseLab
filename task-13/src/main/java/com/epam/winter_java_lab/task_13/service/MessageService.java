package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
import com.epam.winter_java_lab.task_13.exception.MessageNotFoundException;
import com.epam.winter_java_lab.task_13.repos.MessageRepository;
import com.epam.winter_java_lab.task_13.transformer.MessageTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageService {

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
        Page<MessageDto> messages;
        if (!filter.isEmpty()) {
            messages = messageRepository.findByTag(filter, pageable)
                    .map(messageTransformer::transformToDto);
        } else {
            messages = findAllMessages(pageable);
        }
        return messages;
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> getMessagesByCreatedDateTime(Pageable pageable) {
        return messageRepository.findTop10ByOrderByCreatedDateTimeDesc(pageable)
                .map(messageTransformer::transformToDto);
    }

    public void deleteMessageById(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    public void saveMessage(MessageDto messageDto) {
        messageRepository.save(messageTransformer.transformToEntity(messageDto));
    }

    public void editMessage(MessageDto messageDto) {
        Message message = messageTransformer.transformToEntity(messageDto);
        message.setText(messageDto.getText());
        message.setTag(messageDto.getTag());

        saveMessage(messageTransformer.transformToDto(message));
    }

    public MessageDto findMessage(Long id)  {

        return messageTransformer.transformToDto(messageRepository.findMessageById(id)
                .orElseThrow(() -> new MessageNotFoundException(String.format("Message id %d does not exist", id))));
    }

}
