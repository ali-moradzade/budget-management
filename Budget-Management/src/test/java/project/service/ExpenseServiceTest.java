package project.service;

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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceTest {

    @Mock
    private ValidationService validationService;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private ExpenseParticipantRepository expenseParticipantRepository;

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void createExpense_withoutParticipants_shouldSaveOnlyExpense() {

        Long userId = 1L;

        CreateExpenseRequest request =
                new CreateExpenseRequest("food", 100.0, "lunch", null);

        User user = new User("ali");
        Category category = new Category("food", user);

        when(validationService.getUserIfExists(userId)).thenReturn(user);
        when(validationService.getCategoryIfExists(userId, "food")).thenReturn(category);

        expenseService.createExpense(userId, request);

        verify(expenseRepository, times(1)).save(any(Expense.class));
        verify(expenseParticipantRepository, never()).save(any());
    }

    @Test
    void createExpense_withParticipants_shouldCreateParticipants() {

        Long userId = 1L;

        CreateExpenseRequest request =
                new CreateExpenseRequest(
                        "food",
                        100.0,
                        "lunch",
                        List.of(10L, 20L)
                );

        User user = new User("ali");
        Category category = new Category("food", user);

        Friend f1 = new Friend("bob", "111", user);
        Friend f2 = new Friend("john", "222", user);

        when(validationService.getUserIfExists(userId)).thenReturn(user);
        when(validationService.getCategoryIfExists(userId, "food")).thenReturn(category);

        when(validationService.getFriendIfExists(userId, 10L)).thenReturn(f1);
        when(validationService.getFriendIfExists(userId, 20L)).thenReturn(f2);

        expenseService.createExpense(userId, request);

        verify(expenseRepository, times(1)).save(any(Expense.class));
        verify(expenseParticipantRepository, times(2)).save(any(ExpenseParticipant.class));
    }
}