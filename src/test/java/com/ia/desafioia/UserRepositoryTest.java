package com.ia.desafioia;

import com.ia.desafioia.configurations.TestRedisConfiguration;
import com.ia.desafioia.models.User;
import com.ia.desafioia.repositories.interfaces.IUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
public class UserRepositoryTest {

    @Autowired
    private IUserRepository userRepository;

    @Test
    public void shouldSaveUser_toRedis() {
        UUID id = UUID.randomUUID();
        User user = new User();
        User saved = userRepository.save(user);
        assertNotNull(saved);
    }
}