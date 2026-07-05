package project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FriendTest {

    @Test
    void friend_should_store_values_correctly() {
        Friend friend = new Friend();

        friend.setId(1L);
        friend.setFriendName("Ali");
        friend.setFriendPhone("12345");

        assertEquals(1L, friend.getId());
        assertEquals("Ali", friend.getFriendName());
        assertEquals("12345", friend.getFriendPhone());
    }
}