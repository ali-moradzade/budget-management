package project.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.dto.request.CreateExpenseRequest;
import project.dto.request.CreateFriendRequest;
import project.service.*;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ExpenseIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserService userService;

    @Autowired
    private FriendService friendService;

    @Autowired
    private CategoryService categoryService;

    @Test
    void createExpense_withParticipants_shouldPersistCorrectly() {

        String username = "ali-" + UUID.randomUUID();

        Long userId = userService.createUser(username);

        categoryService.createCategory(userId, "food");

        friendService.addFriend(userId,
                new CreateFriendRequest("john", "111"));

        Long friendId = friendService.getUserFriends(userId)
                .get(0)
                .id();

        CreateExpenseRequest request =
                new CreateExpenseRequest("food", 100.0, "dinner", List.of(friendId));

        expenseService.createExpense(userId, request);

        List<?> expenses = expenseService.getUserExpenses(userId);

        assertEquals(1, expenses.size());
    }
}