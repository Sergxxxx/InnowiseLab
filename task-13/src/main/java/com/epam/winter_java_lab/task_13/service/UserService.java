package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.Role;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.dto.UserDto;
import com.epam.winter_java_lab.task_13.exception.UsernameNotFoundException;
import com.epam.winter_java_lab.task_13.repos.UserRepository;
import com.epam.winter_java_lab.task_13.transformer.UserTransformer;
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

    public UserService(UserRepository userRepository, UserTransformer userTransformer) {
        this.userRepository = userRepository;
        this.userTransformer = userTransformer;
    }

    public UserDto loadUserByUsername(String username) throws UsernameNotFoundException {
        return userTransformer.transformToDto(userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s does not exist", username))));
    }

    public UserDto addUser(UserDto userDto){
        return userRepository.findByUsername(userTransformer.transformToEntity(userDto).getUsername())
                .or(() -> Optional.of(createUser(userTransformer.transformToEntity(userDto))))
                .map(userTransformer::transformToDto)
                .orElseThrow();
    }

    private User createUser(User user) {
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.saveAndFlush(user);
        return user;
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userTransformer::transformToDto);
    }

}




