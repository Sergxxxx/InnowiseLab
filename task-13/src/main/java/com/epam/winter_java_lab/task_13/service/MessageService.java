package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
<<<<<<< HEAD
import com.epam.winter_java_lab.task_13.exception.MessageNotFoundException;
import com.epam.winter_java_lab.task_13.repos.MessageRepository;
=======
import com.epam.winter_java_lab.task_13.exception.NotFoundException;
import com.epam.winter_java_lab.task_13.repos.MessageRepo;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
import com.epam.winter_java_lab.task_13.transformer.MessageTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageTransformer messageTransformer;

    public MessageService(MessageRepository messageRepository, MessageTransformer messageTransformer) {
        this.messageRepository = messageRepository;
=======
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final MessageRepo messageRepo;
    private final MessageTransformer messageTransformer;

    public MessageService(MessageRepo messageRepo, MessageTransformer messageTransformer) {
        this.messageRepo = messageRepo;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
        this.messageTransformer = messageTransformer;
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> findAllMessages(Pageable pageable) {
<<<<<<< HEAD
        return messageRepository.findAll(pageable)
                .map(messageTransformer::transformToDto);
=======
        return messageRepo.findAll(pageable)
                .map(messageTransformer::transform);
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> getAllMessagesByUserId(Long userId, Pageable pageable) {
<<<<<<< HEAD
        return messageRepository.findMessagesByUserId(userId, pageable)
                .map(messageTransformer::transformToDto);
=======
        return messageRepo.findMessagesByUserId(userId, pageable)
                .map(messageTransformer::transform);
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

    @Transactional(readOnly = true)
    public Page<MessageDto> findMessagesByFilter(String filter, Pageable pageable) {
        Page<MessageDto> messages;
        if (!filter.isEmpty()) {
<<<<<<< HEAD
            messages = messageRepository.findByTag(filter, pageable)
                    .map(messageTransformer::transformToDto);
=======
            messages = messageRepo.findByTag(filter, pageable)
                    .map(messageTransformer::transform);
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
        } else {
            messages = findAllMessages(pageable);
        }
        return messages;
    }

    @Transactional(readOnly = true)
<<<<<<< HEAD
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
=======
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
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

}
