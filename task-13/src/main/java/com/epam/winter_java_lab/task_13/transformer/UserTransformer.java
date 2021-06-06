package com.epam.winter_java_lab.task_13.transformer;

import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserTransformer {

    public UserDto transform(User user) {
        return UserDto.builder()
                .withId(user.getId())
                .withUsername(user.getUsername())
                .withEmail(user.getEmail())
                .withRegistrationDateTime(user.getRegistrationDateTime())
                .withActive(user.isActive())
                .withRoles(user.getRoles())
                .build();
    }

    public User toEntity(UserDto userDto) {
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
