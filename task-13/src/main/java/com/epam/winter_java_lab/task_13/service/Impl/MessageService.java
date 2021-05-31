package com.epam.winter_java_lab.task_13.service.Impl;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
import com.epam.winter_java_lab.task_13.repos.MessageRepo;
import com.epam.winter_java_lab.task_13.transformer.MessageTransformer;
import com.epam.winter_java_lab.task_13.utils.ErrorUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

import java.util.*;
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

    public void saveMessage(Message message) {
        messageRepo.save(message);
    }

    @Transactional(readOnly = true)
    public List<MessageDto> findMessagesByFilter(String filter, Pageable pageable) {
        List<MessageDto> messages;
        if (!filter.isEmpty()) {
            messages = messageRepo.findByTag(filter, pageable)
                    .map(messageTransformer::transform).collect(Collectors.toList());
        } else {
            messages = findAllMessages(pageable).getContent();
        }
        return messages;
    }

    @Transactional(readOnly = true)
    public List<MessageDto> getMessagesByCreatedDateTime() {
        return messageRepo.getMessagesByCreatedDateTime()
                .map(messageTransformer::transform)
                .collect(Collectors.toList());
    }

    public void deleteMessageById(Long messageId) {
        messageRepo.deleteById(messageId);
    }

    public void editMessage(User currentUser, Message message, String text, String tag) {
        if (message.getAuthor().equals(currentUser)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }

            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }
            message.setUpdatedDateTime(System.currentTimeMillis());
            saveMessage(message);
        }
    }

    public void addMessage(User user, Message message, BindingResult bindingResult) {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            ErrorUtil.getErrors(bindingResult);
        } else {
            message.setCreatedDateTime(System.currentTimeMillis());
            saveMessage(message);
        }
    }

    public Set<Message> getSortMessagesByAsc(User user) {
        Set<Message> sortedMessagesAsc = new TreeSet<>(Comparator.comparing(Message::getCreatedDateTime));
        sortedMessagesAsc.addAll(user.getMessages());

        return sortedMessagesAsc;
    }

    public Set<Message> getSortMessagesByDesc(User user) {
        Set<Message> sortedMessagesDesc = new TreeSet<>((o2, o1) -> o1.getCreatedDateTime().compareTo(o2.getCreatedDateTime()));
        sortedMessagesDesc.addAll(user.getMessages());

        return sortedMessagesDesc;
    }
}
