package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.model.User;
import project.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    ValidationService validationService;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    void createUser_shouldReturnUserId() {

        User saved = new User("ali");
        saved.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(saved);

        Long result = userService.createUser("ali");

        assertEquals(1L, result);
        verify(validationService, times(1)).checkUserNameDuplication("ali");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_shouldCallValidation() {

        User saved = new User("ali");
        saved.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(saved);

        userService.createUser("ali");

        verify(validationService).checkUserNameDuplication("ali");
    }
}