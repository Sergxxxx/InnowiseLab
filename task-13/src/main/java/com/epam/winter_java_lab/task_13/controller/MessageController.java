package com.epam.winter_java_lab.task_13.controller;

import com.epam.winter_java_lab.task_13.CustomAnnotation.ApiPageableMessage;
import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
import com.epam.winter_java_lab.task_13.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public List<MessageDto> greeting() {

        return messageService.getMessagesByCreatedDateTime();
    }

    @GetMapping("/main")
    @ApiPageableMessage
    public Page<MessageDto> main(@RequestParam(required = false, defaultValue = "") String filter, Pageable pageable) {

        return messageService.findMessagesByFilter(filter, pageable);
    }

    @GetMapping("/user-messages")
    @ApiPageableMessage
    public Page<MessageDto> userMessages(@RequestParam("user") Long userId, Pageable pageable) {

        return messageService.getAllMessagesByUserId(userId, pageable);
    }

    @PostMapping("/main")
    public Message add(@RequestBody MessageDto messageDto) {
        messageService.saveMessage(messageDto);

        return messageService.findMessage(messageDto.getId());
    }

    @PutMapping("/update/message")
    public Message updateMessage(@RequestParam("id") Long id,
                                 @RequestParam("text") String text,
                                 @RequestParam("tag") String tag
    ) {
        messageService.editMessage(id, text, tag);

        return messageService.findMessage(id);
    }

    @GetMapping("/del-user-messages/")
    @ApiPageableMessage
    public Page<MessageDto> deleteMessage(@RequestParam("user") Long userId,
                                          @RequestParam("message") Long messageId,
                                          Pageable pageable
    ) {
        messageService.deleteMessageById(messageId);

        return messageService.getAllMessagesByUserId(userId, pageable);
    }

}

