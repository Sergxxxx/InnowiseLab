package com.innowise.task.service;

import com.innowise.task.domain.Role;
import com.innowise.task.domain.User;
import com.innowise.task.dto.UserDto;
import com.innowise.task.exception.NotFoundException;
import com.innowise.task.repository.UserRepository;
import com.innowise.task.transformer.UserTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserTransformer userTransformer;

    private static final String ERROR_USER_REQUEST = "Username %s does not exist";

    public UserService(UserRepository userRepository, UserTransformer userTransformer) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
    }

    public UserDto loadUserByUsername(String username) {

        return userTransformer.transformToDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format(ERROR_USER_REQUEST, username))));
    }

    public UserDto saveUser(UserDto userDto){

        return userRepository.findByUsername(userDto.getUsername())
                .or(() -> Optional.of(createUser(userTransformer.transformToEntity(userDto))))
                .map(userTransformer::transformToDto)
                .orElseThrow();
    }

    public User createUser(User user) {
        user.setRoles(Collections.singleton(Role.USER));

        return userRepository.saveAndFlush(user);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAllUsers(Pageable pageable) {

        return userRepository.findAll(pageable).map(userTransformer::transformToDto);
    }

}