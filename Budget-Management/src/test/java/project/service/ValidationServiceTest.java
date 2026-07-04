package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.exception.ResourceAlreadyExistsException;
import project.exception.ResourceNotFoundException;
import project.model.*;
import project.repository.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseParticipantRepository expenseParticipantRepository;

    @Mock
    private FriendRepository friendRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ValidationService validationService;

    // ----------------------------------------------------
    // getCategoryIfExists
    // ----------------------------------------------------

    @Test
    void getCategoryIfExists_shouldReturnCategory() {
        Long userId = 1L;

        Category category = new Category("Food", new User("ali"));

        when(categoryRepository.findCategoryByUserIdAndName(userId, "Food"))
                .thenReturn(Optional.of(category));

        Category result = validationService.getCategoryIfExists(userId, "Food");

        assertEquals(category, result);
    }

    @Test
    void getCategoryIfExists_shouldReturnNull_whenCategoryNameIsNull() {

        Category result = validationService.getCategoryIfExists(1L, null);

        assertNull(result);

        verify(categoryRepository, never()).findCategoryByUserIdAndName(anyLong(), anyString());
    }

    @Test
    void getCategoryIfExists_shouldThrow_whenCategoryNotFound() {

        when(categoryRepository.findCategoryByUserIdAndName(1L, "Food"))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> validationService.getCategoryIfExists(1L, "Food"));
    }

    // ----------------------------------------------------
    // getExpenseIfExists
    // ----------------------------------------------------

    @Test
    void getExpenseIfExists_shouldReturnExpense() {

        Expense expense = new Expense();

        when(expenseRepository.findExpenseByUserIdAndId(1L, 10L))
                .thenReturn(Optional.of(expense));

        Expense result = validationService.getExpenseIfExists(1L, 10L);

        assertEquals(expense, result);
    }

    @Test
    void getExpenseIfExists_shouldThrow_whenNotFound() {

        when(expenseRepository.findExpenseByUserIdAndId(1L, 10L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> validationService.getExpenseIfExists(1L, 10L));
    }

    // ----------------------------------------------------
    // getExpenseParticipantIfExists
    // ----------------------------------------------------

    @Test
    void getExpenseParticipantIfExists_shouldReturnParticipant() {

        Expense expense = new Expense();
        expense.setId(100L);

        ExpenseParticipant participant = new ExpenseParticipant();
        participant.setExpense(expense);

        when(expenseParticipantRepository.findById(5L))
                .thenReturn(Optional.of(participant));

        when(expenseRepository.findExpenseByUserIdAndId(1L, 100L))
                .thenReturn(Optional.of(expense));

        ExpenseParticipant result =
                validationService.getExpenseParticipantIfExists(1L, 5L);

        assertEquals(participant, result);
    }

    @Test
    void getExpenseParticipantIfExists_shouldThrow_whenParticipantMissing() {

        when(expenseParticipantRepository.findById(5L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> validationService.getExpenseParticipantIfExists(1L, 5L));
    }

    // ----------------------------------------------------
    // getFriendIfExists
    // ----------------------------------------------------

    @Test
    void getFriendIfExists_shouldReturnFriend() {

        Friend friend = new Friend();

        when(friendRepository.findFriendByUserIdAndId(1L, 7L))
                .thenReturn(Optional.of(friend));

        Friend result = validationService.getFriendIfExists(1L, 7L);

        assertEquals(friend, result);
    }

    @Test
    void getFriendIfExists_shouldThrow_whenMissing() {

        when(friendRepository.findFriendByUserIdAndId(1L, 7L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> validationService.getFriendIfExists(1L, 7L));
    }

    // ----------------------------------------------------
    // getUserIfExists
    // ----------------------------------------------------

    @Test
    void getUserIfExists_shouldReturnUser() {

        User user = new User("ali");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        User result = validationService.getUserIfExists(1L);

        assertEquals(user, result);
    }

    @Test
    void getUserIfExists_shouldThrow_whenMissing() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> validationService.getUserIfExists(1L));
    }

    // ----------------------------------------------------
    // getAllFriends
    // ----------------------------------------------------

    @Test
    void getAllFriends_shouldReturnFriends() {

        List<Friend> friends = List.of(
                new Friend(),
                new Friend()
        );

        when(friendRepository.findByUserId(1L))
                .thenReturn(friends);

        List<Friend> result = validationService.getAllFriends(1L);

        assertEquals(2, result.size());
    }

    // ----------------------------------------------------
    // Duplication checks
    // ----------------------------------------------------

    @Test
    void checkCategoryNameDuplication_shouldPass() {

        when(categoryRepository.findCategoryByUserIdAndName(1L, "Food"))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() ->
                validationService.checkCategoryNameDuplication(1L, "Food"));
    }

    @Test
    void checkCategoryNameDuplication_shouldThrow() {

        when(categoryRepository.findCategoryByUserIdAndName(1L, "Food"))
                .thenReturn(Optional.of(new Category()));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> validationService.checkCategoryNameDuplication(1L, "Food"));
    }

    @Test
    void checkFriendNameDuplication_shouldPass() {

        when(friendRepository.findFriendByUserIdAndFriendName(1L, "Bob"))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() ->
                validationService.checkFriendNameDuplication(1L, "Bob"));
    }

    @Test
    void checkFriendNameDuplication_shouldThrow() {

        when(friendRepository.findFriendByUserIdAndFriendName(1L, "Bob"))
                .thenReturn(Optional.of(new Friend()));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> validationService.checkFriendNameDuplication(1L, "Bob"));
    }

    @Test
    void checkUserNameDuplication_shouldPass() {

        when(userRepository.findUserByUsername("ali"))
                .thenReturn(Optional.empty());

        assertDoesNotThrow(() ->
                validationService.checkUserNameDuplication("ali"));
    }

    @Test
    void checkUserNameDuplication_shouldThrow() {

        when(userRepository.findUserByUsername("ali"))
                .thenReturn(Optional.of(new User()));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> validationService.checkUserNameDuplication("ali"));
    }

}
