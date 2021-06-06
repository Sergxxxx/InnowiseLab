package com.epam.winter_java_lab.task_13.controller;

import com.epam.winter_java_lab.task_13.CustomAnnotation.ApiPageableUser;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.dto.UserDto;
import com.epam.winter_java_lab.task_13.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ApiPageableUser
    public Page<UserDto> userList(Pageable pageable){

        return userService.findAllUsers(pageable);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<User> userSave(@RequestBody UserDto userDto
    ){
        userService.addUser(userDto);

        return new ResponseEntity("User saved successfully", HttpStatus.OK);
    }

}
