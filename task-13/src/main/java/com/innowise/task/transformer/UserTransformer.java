package com.innowise.task.transformer;

import com.innowise.task.domain.User;
import com.innowise.task.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {

    public UserDto transformToDto(User user) {
        return UserDto.builder()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withRegistrationDateTime(user.getRegistrationDateTime())
                .withActive(user.isActive())
                .withRoles(user.getRoles())
                .build();
    }

    public User transformToEntity(UserDto userDto) {
        return User.builder()
                .withId(userDto.getId())
                .withUsername(userDto.getUsername())
                .withEmail(userDto.getEmail())
                .withRegistrationDateTime(userDto.getRegistrationDateTime())
                .withActive(userDto.isActive())
                .withRoles(userDto.getRoles())
                .build();
    }

}