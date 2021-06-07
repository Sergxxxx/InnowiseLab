package com.epam.winter_java_lab.task_13.controller;

import com.epam.winter_java_lab.task_13.customAnnotation.ApiPageableMessage;
import com.epam.winter_java_lab.task_13.dto.MessageDto;
import com.epam.winter_java_lab.task_13.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    @ApiPageableMessage
    public Page<MessageDto> greeting(Pageable pageable) {

        return messageService.getMessagesByCreatedDateTime(pageable);
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
    public MessageDto add(@RequestBody MessageDto messageDto) {
        messageService.saveMessage(messageDto);

        return messageService.findMessage(messageDto.getId());
    }

    @PutMapping("/update/message")
    public MessageDto updateMessage(@RequestBody MessageDto messageDto
    ) {
        messageService.editMessage(messageDto);

        return messageService.findMessage(messageDto.getId());
    }

    @GetMapping("/del-user-messages/")
    @ApiPageableMessage
    public Page<MessageDto> deleteMessage(@RequestParam("user") Long userId,
                                          @RequestParam("message") Long messageId
    ) {
        messageService.deleteMessageById(messageId);

        return messageService.getAllMessagesByUserId(userId, Pageable.unpaged());
    }

}

