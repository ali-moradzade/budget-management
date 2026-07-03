package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.dto.request.CreateFriendRequest;
import project.model.Friend;
import project.model.User;
import project.repository.FriendRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FriendServiceTest {

    @Mock
    ValidationService validationService;

    @Mock
    FriendRepository friendRepository;

    @InjectMocks
    FriendService friendService;

    @Test
    void addFriend_shouldSaveFriend() {

        User user = new User("ali");
        user.setId(1L);

        CreateFriendRequest request =
                new CreateFriendRequest("john", "123");

        when(validationService.getUserIfExists(1L)).thenReturn(user);

        friendService.addFriend(1L, request);

        verify(validationService).checkFriendNameDuplication(1L, "john");
        verify(friendRepository).save(any(Friend.class));
    }

    @Test
    void getUserFriends_shouldReturnList() {

        User user = new User("ali");
        user.setId(1L);

        Friend f = new Friend("john", "123", user);

        when(validationService.getUserIfExists(1L)).thenReturn(user);
        when(friendRepository.findByUserId(1L)).thenReturn(List.of(f));

        var result = friendService.getUserFriends(1L);

        assertEquals(1, result.size());
        verify(validationService).getUserIfExists(1L);
    }
}