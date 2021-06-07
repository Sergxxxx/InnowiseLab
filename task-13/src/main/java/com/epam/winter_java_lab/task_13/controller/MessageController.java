package com.epam.winter_java_lab.task_13.controller;

<<<<<<< HEAD
import com.epam.winter_java_lab.task_13.customAnnotation.ApiPageableMessage;
=======
import com.epam.winter_java_lab.task_13.CustomAnnotation.ApiPageableMessage;
import com.epam.winter_java_lab.task_13.domain.Message;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
import com.epam.winter_java_lab.task_13.dto.MessageDto;
import com.epam.winter_java_lab.task_13.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
@RestController
@RequestMapping("/api")
public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
<<<<<<< HEAD
    @ApiPageableMessage
    public Page<MessageDto> greeting(Pageable pageable) {

        return messageService.getMessagesByCreatedDateTime(pageable);
=======
    public List<MessageDto> greeting() {

        return messageService.getMessagesByCreatedDateTime();
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
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
<<<<<<< HEAD
    public MessageDto add(@RequestBody MessageDto messageDto) {
=======
    public Message add(@RequestBody MessageDto messageDto) {
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
        messageService.saveMessage(messageDto);

        return messageService.findMessage(messageDto.getId());
    }

    @PutMapping("/update/message")
<<<<<<< HEAD
    public MessageDto updateMessage(@RequestBody MessageDto messageDto
    ) {
        messageService.editMessage(messageDto);

        return messageService.findMessage(messageDto.getId());
=======
    public Message updateMessage(@RequestParam("id") Long id,
                                 @RequestParam("text") String text,
                                 @RequestParam("tag") String tag
    ) {
        messageService.editMessage(id, text, tag);

        return messageService.findMessage(id);
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

    @GetMapping("/del-user-messages/")
    @ApiPageableMessage
    public Page<MessageDto> deleteMessage(@RequestParam("user") Long userId,
<<<<<<< HEAD
                                          @RequestParam("message") Long messageId
    ) {
        messageService.deleteMessageById(messageId);

        return messageService.getAllMessagesByUserId(userId, Pageable.unpaged());
=======
                                          @RequestParam("message") Long messageId,
                                          Pageable pageable
    ) {
        messageService.deleteMessageById(messageId);

        return messageService.getAllMessagesByUserId(userId, pageable);
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    }

}

