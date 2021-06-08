package com.innowise.task;

import com.innowise.task.domain.User;
import com.innowise.task.dto.UserDto;
import com.innowise.task.repository.UserRepository;
import com.innowise.task.service.UserService;
import com.innowise.task.transformer.UserTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    private static final User USER = new User();
    private static final UserDto USER_DTO = new UserDto();
    private static final Page<UserDto> EXPECTED_PAGE = new PageImpl<>(Collections.singletonList(USER_DTO));
    private static final Page<User> PAGE = new PageImpl<>(Collections.singletonList(USER));

    private static final Pageable PAGEABLE = Pageable.unpaged();

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTransformer userTransformer;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldLoadUserByUsername() {
        given(userRepository.findByUsername(USER.getUsername())).willReturn(Optional.of(USER));
        given(userTransformer.transformToDto(USER)).willReturn(USER_DTO);

        Assertions.assertEquals(USER_DTO, userService.loadUserByUsername(USER.getUsername()));

        then(userRepository).should(only()).findByUsername(USER.getUsername());
        then(userTransformer).should(only()).transformToDto(USER);
    }

    @Test
    public void shouldFindAllUsers() {
        given(userRepository.findAll(PAGEABLE)).willReturn(PAGE);
        given(userTransformer.transformToDto(USER)).willReturn(USER_DTO);

        assertEquals(EXPECTED_PAGE, userService.findAllUsers(PAGEABLE));

        then(userRepository).should(only()).findAll(PAGEABLE);
        then(userTransformer).should(only()).transformToDto(USER);
    }

    @Test
    public void shouldCreateUser() {
        userService.createUser(USER);

        then(userRepository).should(only()).saveAndFlush(USER);
    }

}