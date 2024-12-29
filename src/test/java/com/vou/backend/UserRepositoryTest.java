package com.vou.backend;

import com.vou.backend.user.model.User;
import com.vou.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByIdIn() {

        List<Long> userIds = Arrays.asList(1L, 2L);

        // When
        List<User> users = userRepository.findByIds(userIds);

        // Then
        assertThat(users).hasSize(2);
       // assertThat(users).extracting(User::getUsername).containsExactlyInAnyOrder("user1", "user2");
    }
}
