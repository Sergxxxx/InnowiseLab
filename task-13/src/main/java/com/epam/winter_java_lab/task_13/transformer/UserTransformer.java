package com.epam.winter_java_lab.task_13.transformer;

import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.dto.UserDto;
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

<<<<<<< HEAD
    public User transformToEntity(UserDto userDto) {
=======
    public User toEntity(UserDto userDto) {
>>>>>>> 04818681b3ffc775807441cf756a5b5e07d1c8ed
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
