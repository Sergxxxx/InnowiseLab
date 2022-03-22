package com.innowise.task.controller;

import com.innowise.task.extention.ApiPageableUser;
import com.innowise.task.dto.UserDto;
import com.innowise.task.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiPageableUser
    public Page<UserDto> userList(Pageable pageable){

        return userService.findAllUsers(pageable);
    }

    @PostMapping()
    public ResponseEntity<UserDto> userSave(@RequestBody UserDto userDto) {
        UserDto savedUserDto = userService.saveUser(userDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUserDto.getId()).toUri();

        return ResponseEntity.created(location).body(savedUserDto);
    }

}