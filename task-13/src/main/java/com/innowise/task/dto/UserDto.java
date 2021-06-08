package com.innowise.task.dto;

import com.innowise.task.domain.Role;
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

    private boolean active  = true;

    private Set<Role> roles;
}