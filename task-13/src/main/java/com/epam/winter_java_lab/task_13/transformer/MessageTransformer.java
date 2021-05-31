package com.epam.winter_java_lab.task_13.transformer;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
import org.springframework.stereotype.Component;

@Component
public class MessageTransformer {

    public MessageDto transform(Message message) {
        return MessageDto.builder()
                .withId(message.getId())
                .withText(message.getText())
                .withTag(message.getTag())
                .withCreatedDateTime(message.getCreatedDateTime())
                .withUpdatedDateTime(message.getUpdatedDateTime())
                .withAuthor(message.getAuthor())
                .build();
    }
}
