package com.innowise.task.integration.repository;

import com.innowise.task.extention.PostgreSQLTestContainer;
import com.innowise.task.domain.User;
import com.innowise.task.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@SqlGroup({
        @Sql("/sql/dataUser.sql"),
        @Sql("/sql/dataMessage.sql")
})
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles({"test", "enable-jpa-audition"})
@PostgreSQLTestContainer
@DirtiesContext
public class UserRepositoryTest {

    private static final Pageable PAGEABLE = PageRequest.of(0, 20);

    private static final User USER_ONE = User.builder()
            .withId(1L)
            .withPassword("1")
            .withUsername("1")
            .withEmail("1@1.com")
            .withActive(true)
            .build();

    private static final User USER_TWO = User.builder()
            .withId(2L)
            .withPassword("2")
            .withUsername("user2")
            .withEmail("2@2.com")
            .withActive(true)
            .build();

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindByUsername() {
      assertEquals(Optional.of(USER_TWO), userRepository.findByUsername("user2"));
    }

    @Test
    void shouldFindAllUsers() {
        assertEquals(Optional.of(USER_ONE), userRepository.findAll(PAGEABLE).get().findFirst());
        assertEquals(2, userRepository.findAll(PAGEABLE).getTotalElements());
        assertEquals(1, userRepository.findAll(PAGEABLE).getTotalPages());
    }

}