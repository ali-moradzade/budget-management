package project.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.dto.request.CreateFriendRequest;
import project.service.FriendService;
import project.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class FriendIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private FriendService friendService;

    @Autowired
    private UserService userService;

    @Test
    void addFriend_shouldPersistFriend() {

        String username = "ali-" + UUID.randomUUID();

        Long userId = userService.createUser(username);

        friendService.addFriend(userId,
                new CreateFriendRequest("john", "123"));

        List<?> friends = friendService.getUserFriends(userId);

        assertEquals(1, friends.size());
    }
}