package project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.dto.request.CreateExpenseRequest;
import project.dto.response.ExpenseResponse;
import project.service.ExpenseService;
import java.util.List;

@Tag(name = "Expenses", description = "Expense management endpoints")
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(summary = "Create a new expense record")
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createExpense(@PathVariable Long userId, @RequestBody CreateExpenseRequest request) {
        request.validate();
        expenseService.createExpense(userId, request);
    }

    @Operation(summary = "Get list of your expenses")
    @GetMapping("/{userId}")
    public List<ExpenseResponse> getUserExpenses(@PathVariable Long userId) {
        return expenseService.getUserExpenses(userId);
    }

    @Operation(summary = "Get one of your expenses by id")
    @GetMapping("/{userId}/{expenseId}")
    public ExpenseResponse getExpense(@PathVariable Long userId, @PathVariable Long expenseId) {
        return expenseService.getExpense(userId, expenseId);
    }
}
