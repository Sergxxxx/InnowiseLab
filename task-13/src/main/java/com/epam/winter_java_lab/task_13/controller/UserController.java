package com.epam.winter_java_lab.task_13.controller;

import com.epam.winter_java_lab.task_13.domain.Role;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.dto.UserDto;
import com.epam.winter_java_lab.task_13.service.Impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(value = "user resources")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    @ApiOperation(value = "show all users", response = List.class)
    public Page<UserDto> userList(){

        return userService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    @ApiOperation(value = "edit user role", response = ResponseEntity.class)
    public ResponseEntity userEditForm(@PathVariable User user){
        Role.values();

        return new ResponseEntity("user " + user.getUsername(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    @ApiOperation(value = "save user", response = ResponseEntity.class)
    public ResponseEntity userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ){
        userService.saveUser(user, username, form);

        return new ResponseEntity("User saved successfully", HttpStatus.OK);
    }

}
