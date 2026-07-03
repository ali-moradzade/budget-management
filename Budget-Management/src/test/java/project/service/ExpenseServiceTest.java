package project.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.dto.request.CreateExpenseRequest;
import project.model.*;
import project.repository.ExpenseParticipantRepository;
import project.repository.ExpenseRepository;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    ValidationService validationService;

    @Mock
    ExpenseRepository expenseRepository;

    @Mock
    ExpenseParticipantRepository expenseParticipantRepository;

    @InjectMocks
    ExpenseService expenseService;

    private User user;
    private Category category;
    private Friend friend;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        category = new Category();
        category.setId(1L);
        category.setName("food");

        friend = new Friend();
        friend.setId(2L);
        friend.setFriendName("Ali");
    }

    @Test
    void createExpense_withoutParticipants_shouldSaveOnlyExpense() {

        CreateExpenseRequest request =
                new CreateExpenseRequest("test", 100.0, "food", null);

        when(validationService.getUserIfExists(eq(1L))).thenReturn(user);
        when(validationService.getCategoryIfExists(eq(1L), anyString()))
                .thenReturn(category);

        expenseService.createExpense(1L, request);

        verify(expenseRepository, times(1)).save(any(Expense.class));
        verify(expenseParticipantRepository, never()).save(any());
    }

    @Test
    void createExpense_withParticipants_shouldCreateParticipants() {

        CreateExpenseRequest request =
                new CreateExpenseRequest("test", 100.0, "food", List.of(2L));

        when(validationService.getUserIfExists(eq(1L))).thenReturn(user);
        when(validationService.getCategoryIfExists(eq(1L), anyString()))
                .thenReturn(category);

        when(validationService.getFriendIfExists(eq(1L), eq(2L)))
                .thenReturn(friend);

        expenseService.createExpense(1L, request);

        verify(expenseRepository, times(1)).save(any(Expense.class));
        verify(expenseParticipantRepository, times(1))
                .save(any(ExpenseParticipant.class));
    }

    @Test
    void createExpense_invalidFriend_shouldThrow() {

        CreateExpenseRequest request =
                new CreateExpenseRequest("test", 100.0, "food", List.of(99L));

        when(validationService.getUserIfExists(eq(1L))).thenReturn(user);
        when(validationService.getCategoryIfExists(eq(1L), anyString()))
                .thenReturn(category);

        when(validationService.getFriendIfExists(eq(1L), eq(99L)))
                .thenThrow(new RuntimeException("Friend not found"));

        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> expenseService.createExpense(1L, request)
        );
    }
}