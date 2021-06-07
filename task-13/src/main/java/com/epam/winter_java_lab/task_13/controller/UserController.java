package com.epam.winter_java_lab.task_13.controller;

<<<<<<< HEAD
import com.epam.winter_java_lab.task_13.customAnnotation.ApiPageableUser;
=======
import com.epam.winter_java_lab.task_13.CustomAnnotation.ApiPageableUser;
import com.epam.winter_java_lab.task_13.domain.User;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
import com.epam.winter_java_lab.task_13.dto.UserDto;
import com.epam.winter_java_lab.task_13.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
<<<<<<< HEAD
=======
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
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

    @PostMapping
<<<<<<< HEAD
    public UserDto userSave(@RequestBody UserDto userDto
=======
    public ResponseEntity<User> userSave(@RequestBody UserDto userDto
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
    ){
        userService.addUser(userDto);

        return userService.loadUserByUsername(userDto.getUsername());
    }

}
