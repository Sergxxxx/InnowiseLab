package com.epam.winter_java_lab.task_13.transformer;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
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

<<<<<<< HEAD
    public Message transformToEntity(MessageDto messageDto) {
=======
    public Message toEntity(MessageDto messageDto) {
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
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
