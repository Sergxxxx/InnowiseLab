package com.innowise.task;

import com.innowise.task.controller.UserController;
import com.innowise.task.service.UserService;
import com.innowise.task.dto.UserDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
    private static final UserDto USER_DTO = new UserDto();
    private static final Page<UserDto>  PAGE_DTO = new PageImpl<>(Collections.singletonList(USER_DTO));
    private static final Pageable PAGEABLE = Pageable.unpaged();

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void shouldGetUserList()  {
        given(userService.findAllUsers(PAGEABLE)).willReturn(PAGE_DTO);

        assertEquals(PAGE_DTO, userController.userList(PAGEABLE));

        then(userService).should(only()).findAllUsers(PAGEABLE);
    }

}