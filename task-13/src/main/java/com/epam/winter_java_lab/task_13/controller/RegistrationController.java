package com.epam.winter_java_lab.task_13.controller;

import com.epam.winter_java_lab.task_13.domain.User;
//import com.epam.winter_java_lab.task_13.service.UserService;
import com.epam.winter_java_lab.task_13.service.Impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "registration")
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    @ApiOperation(value = "registration", response = ResponseEntity.class)
        public ResponseEntity registration(){

        return new ResponseEntity("registration", HttpStatus.OK);
    }

    @PostMapping("/registration")
    @ApiOperation(value = "registration new user", response = ResponseEntity.class)
    public ResponseEntity addUser(@Valid User user, BindingResult bindingResult, Model model){
        if(user.getPassword() != null && !user.getPassword().equals(user.getPasswordConf())){

            return new ResponseEntity("Password mismatch", HttpStatus.BAD_REQUEST);
        }

        if(bindingResult.hasErrors()){

            return new ResponseEntity("User has not been added", HttpStatus.BAD_REQUEST);
        }

//        if(!userService.addUser(user)){
//
//            return new ResponseEntity("User already exists", HttpStatus.BAD_REQUEST);
//        }

        return new ResponseEntity("User added successfully", HttpStatus.OK);
    }

}











