package com.epam.winter_java_lab.task_13.dto;

import com.epam.winter_java_lab.task_13.domain.Message;
import com.epam.winter_java_lab.task_13.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private LocalDateTime registrationDateTime;

    private boolean active;

    private Set<Role> roles;

    private Set<Message> messages;
}
