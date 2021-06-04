package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
import com.epam.winter_java_lab.task_13.exception.NotFoundException;
import com.epam.winter_java_lab.task_13.repos.MessageRepo;
import com.epam.winter_java_lab.task_13.transformer.MessageTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepo messageRepo;
    private final MessageTransformer messageTransformer;

    public MessageService(MessageRepo messageRepo, MessageTransformer messageTransformer) {
        this.messageRepo = messageRepo;
        this.messageTransformer = messageTransformer;
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> findAllMessages(Pageable pageable) {
        return messageRepo.findAll(pageable)
                .map(messageTransformer::transform);
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> getAllMessagesByUserId(Long userId, Pageable pageable) {
        return messageRepo.findMessagesByUserId(userId, pageable)
                .map(messageTransformer::transform);
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> findMessagesByFilter(String filter, Pageable pageable) {
        Page<MessageDto> messages;
        if (!filter.isEmpty()) {
            messages = messageRepo.findByTag(filter, pageable)
                    .map(messageTransformer::transform);
        } else {
            messages = findAllMessages(pageable);
        }
        return messages;
    }

    @Transactional(readOnly = true)
    public List<MessageDto> getMessagesByCreatedDateTime() {
        return messageRepo.getMessagesByCreatedDateTime()
                .map(messageTransformer::transform).collect(Collectors.toList());
    }

    public void deleteMessageById(Long messageId) {
        messageRepo.deleteById(messageId);
    }

    public void saveMessage(MessageDto messageDto) {
        messageRepo.save(messageTransformer.toEntity(messageDto));
    }

    public void editMessage(Long id, String text, String tag) {
        Message message = findMessage(id);
        message.setText(text);
        message.setTag(tag);
        saveMessage(messageTransformer.transform(message));
    }

    public Message findMessage(Long id) {

        return messageRepo.findMessageById(id)
                .orElseThrow(() -> new NotFoundException("Message id '" + id + "' does no exist"));
    }

}
