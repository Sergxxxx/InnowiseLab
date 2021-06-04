package com.epam.winter_java_lab.task_13.service;

import com.epam.winter_java_lab.task_13.domain.Role;
import com.epam.winter_java_lab.task_13.domain.User;
import com.epam.winter_java_lab.task_13.dto.UserDto;
import com.epam.winter_java_lab.task_13.repos.UserRepo;
import com.epam.winter_java_lab.task_13.transformer.UserTransformer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final UserTransformer userTransformer;

    public UserService(UserRepo userRepo, UserTransformer userTransformer) {
        this.userRepo = userRepo;
        this.userTransformer = userTransformer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDto addUser(UserDto userDto){
        return userRepo.findByUsername(userTransformer.toEntity(userDto).getUsername())
                .or(() -> Optional.of(createUser(userTransformer.toEntity(userDto))))
                .map(userTransformer::transform)
                .orElseThrow();
    }

    private User createUser(User user) {
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.saveAndFlush(user);
        return user;
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAllUsers(Pageable pageable) {
        return userRepo.findAll(pageable).map(userTransformer::transform);
    }

}




