package com.epam.winter_java_lab.task_13.controller;

import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
        public ResponseEntity registration(){

        return new ResponseEntity("registration", HttpStatus.OK);
    }

//    @PostMapping("/registration")
//    public ResponseEntity addUser(@Valid User user, BindingResult bindingResult){
//        if(user.getPassword() != null && !user.getPassword().equals(user.getPasswordConf())){
//
//            return new ResponseEntity("Password mismatch", HttpStatus.BAD_REQUEST);
//        }
//
//        if(bindingResult.hasErrors()){
//
//            return new ResponseEntity("User has not been added", HttpStatus.BAD_REQUEST);
//        }
//
//        return new ResponseEntity("User added successfully", HttpStatus.OK);
//    }

}











