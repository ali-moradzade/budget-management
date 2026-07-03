package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.model.*;
import project.repository.ExpenseParticipantRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExpenseParticipantServiceTest {

    @Mock
    private ValidationService validationService;

    @Mock
    private ExpenseParticipantRepository repository;

    @InjectMocks
    private ExpenseParticipantService service;

    @Test
    void getExpenseParticipants_shouldReturnList() {

        Long userId = 1L;
        Long expenseId = 100L;

        when(validationService.getUserIfExists(userId)).thenReturn(new User("ali"));
        when(validationService.getExpenseIfExists(userId, expenseId))
                .thenReturn(new Expense());

        ExpenseParticipant ep = mock(ExpenseParticipant.class);

        when(repository.findByExpenseId(expenseId))
                .thenReturn(List.of(ep));

        var result = service.getExpenseParticipants(userId, expenseId);

        assertEquals(1, result.size());
    }

    @Test
    void getDebt_shouldReturnSum() {

        Long userId = 1L;
        Long friendId = 10L;

        Friend friend = new Friend("bob", "111", new User("ali"));

        when(validationService.getUserIfExists(userId)).thenReturn(new User("ali"));
        when(validationService.getFriendIfExists(userId, friendId)).thenReturn(friend);

        ExpenseParticipant ep = mock(ExpenseParticipant.class);

        when(ep.getShareAmount()).thenReturn(BigDecimal.valueOf(50));

        when(repository.findByFriendIdAndPaidFalse(friendId))
                .thenReturn(List.of(ep));

        var result = service.getDebt(userId, friendId);

        assertEquals("bob", result.friendName());
        assertEquals(50.0, result.debt());
    }

    @Test
    void markAsPaid_shouldUpdateEntity() {

        Long userId = 1L;
        Long id = 5L;

        ExpenseParticipant ep = new ExpenseParticipant();

        when(validationService.getUserIfExists(userId)).thenReturn(new User("ali"));
        when(validationService.getExpenseParticipantIfExists(userId, id))
                .thenReturn(ep);

        service.markAsPaid(userId, id);

        assertTrue(ep.getPaid());
        verify(repository).save(ep);
    }
}