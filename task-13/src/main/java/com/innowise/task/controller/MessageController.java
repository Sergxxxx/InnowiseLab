package com.innowise.task.controller;

import com.innowise.task.extention.ApiPageableMessage;
import com.innowise.task.dto.MessageDto;
import com.innowise.task.service.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
public class MessageController {

    public static final String MESSAGE_BASE_URL = "/message";
    public static final String MESSAGES_BASE_URL = "/messages";

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(MESSAGES_BASE_URL)
    @ApiPageableMessage
    public Page<MessageDto> getLatestMessages() {

        return messageService.getMessagesByCreatedDateTime();
    }

    @ApiPageableMessage
    @GetMapping(MESSAGES_BASE_URL+"/filter")
    public Page<MessageDto> getAllMessagesByTag(@RequestParam(required = false) String tag,
                                                Pageable pageable) {

        return messageService.findMessagesByFilter(tag, pageable);
    }

    @GetMapping(MESSAGES_BASE_URL+"/userId")
    @ApiPageableMessage
    public Page<MessageDto> userMessages(@RequestParam("userId") Long userId,
                                         Pageable pageable) {

        return messageService.getAllMessagesByUserId(userId, pageable);
    }

    @PostMapping(MESSAGE_BASE_URL+"/add")
    public ResponseEntity<MessageDto> messageSave(@RequestBody MessageDto messageDto) {
        MessageDto savedMessageDto = messageService.saveMessageDto(messageDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedMessageDto.getId()).toUri();

        return ResponseEntity.created(location).body(savedMessageDto);
    }

    @PutMapping(MESSAGE_BASE_URL+"/update/{id}")
    public ResponseEntity<MessageDto> updateMessage(@RequestBody MessageDto messageDto,
                                                    @PathVariable Long id) {
        messageService.editMessage(messageDto);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(MESSAGE_BASE_URL+"/rollback")
    public void rollbackMessage(@RequestBody LocalDateTime desired_date) {
        messageService.rollbackMessageTable(desired_date);
    }

    @DeleteMapping(MESSAGE_BASE_URL+"/delete/{id}")
    public void deleteMessage(@PathVariable Long id) {
        messageService.deleteMessageById(id);
    }

}