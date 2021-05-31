package com.epam.winter_java_lab.task_13.service.Impl;

import com.epam.winter_java_lab.task_13.domain.Role;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.repos.UserRepo;
//import com.epam.winter_java_lab.task_13.service.UserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserService userService;

    @MockBean
    private UserRepo userRepo;

//    @Test
//    public void addUserTest() {
//        User user = new User();
//        boolean isUserCreated = userService.addUser(user);
//
//        assertTrue(isUserCreated);
//        assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
//
//        Mockito.verify(userRepo, Mockito.times(1)).save(user);
//    }

//    @Test
//    public void addUserErrorTest(){
//        User user = new User();
//        user.setUsername("Serg");
//
//        Mockito.doReturn(new User()).when(userRepo).findByUsername("Serg");
//
//        boolean isUserCreated = userService.addUser(user);
//
//        assertFalse(isUserCreated);
//
//        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
//
//    }

//    @Test
//    public void findAllTest(){
//        when(userRepo.findAll()).thenReturn(Stream.of(new User(), new User()).collect(Collectors.toList()));
//
//        assertEquals(2, userService.findAll().size());
//    }


}