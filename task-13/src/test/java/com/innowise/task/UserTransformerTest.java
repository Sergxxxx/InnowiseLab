package com.innowise.task;

import com.innowise.task.domain.Role;
import com.innowise.task.domain.User;
import com.innowise.task.dto.UserDto;
import com.innowise.task.transformer.UserTransformer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTransformerTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final LocalDateTime REGISTRATION_DATE = LocalDateTime.parse("2020-10-10 10:10", FORMATTER);

    private static final Set<Role> ROLE_SET = new HashSet<>();
    private final UserTransformer userTransformer = new UserTransformer();

    private static final User USER = User.builder()
        .withId(1L)
        .withUsername("username")
        .withEmail("1@1.com")
        .withRegistrationDateTime(REGISTRATION_DATE)
        .withActive(true)
        .withRoles(ROLE_SET)
        .build();

    private static final UserDto USER_DTO = UserDto.builder()
        .withId(1L)
        .withUsername("username")
        .withEmail("1@1.com")
        .withRegistrationDateTime(REGISTRATION_DATE)
        .withActive(true)
        .withRoles(ROLE_SET)
        .build();

    @Test
    public void shouldTransformToDto()  {
        assertEquals(USER_DTO, userTransformer.transformToDto(USER));
    }

    @Test
    public void shouldTransformToEntity()  {
        assertEquals(USER, userTransformer.transformToEntity(USER_DTO));
    }

}