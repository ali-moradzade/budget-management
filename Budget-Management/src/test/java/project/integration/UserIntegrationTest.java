package project.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    void createUser_shouldPersistUser() {
        String username = "ali-" + java.util.UUID.randomUUID();

        Long userId = userService.createUser(username);

        assertNotNull(userId);
    }

    @Test
    void createUser_duplicate_shouldThrowException() {
        String username = "ali-" + java.util.UUID.randomUUID();

        userService.createUser(username);

        assertThrows(RuntimeException.class, () -> {
            userService.createUser(username);
        });
    }
}