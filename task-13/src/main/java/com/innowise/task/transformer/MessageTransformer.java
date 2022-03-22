package com.innowise.task.transformer;

import com.innowise.task.domain.Message;
import com.innowise.task.dto.MessageDto;
import org.springframework.stereotype.Component;

@Component
public class MessageTransformer {

    public MessageDto transformToDto(Message message) {
        return MessageDto.builder()
                .withId(message.getId())
                .withText(message.getText())
                .withTag(message.getTag())
                .withCreatedDateTime(message.getCreatedDateTime())
                .withUpdatedDateTime(message.getUpdatedDateTime())
                .withUserId(message.getUserId())
                .build();
    }

    public Message transformToEntity(MessageDto messageDto) {
        return Message.builder()
                .withId(messageDto.getId())
                .withText(messageDto.getText())
                .withTag(messageDto.getTag())
                .withCreatedDateTime(messageDto.getCreatedDateTime())
                .withUpdatedDateTime(messageDto.getUpdatedDateTime())
                .withUserId(messageDto.getUserId())
                .build();
    }

}