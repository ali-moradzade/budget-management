package project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import project.dto.response.DebtResponse;
import project.dto.response.ExpenseParticipantResponse;
import project.service.ExpenseParticipantService;
import java.util.List;

@Tag(name = "Expense Participants", description = "Participant management endpoints")
@RestController
@RequestMapping("/expense-participants")
public class ExpenseParticipantController {

    private final ExpenseParticipantService expenseParticipantService;

    public ExpenseParticipantController(ExpenseParticipantService expenseParticipantService) {
        this.expenseParticipantService = expenseParticipantService;
    }

    @Operation(summary = "Get list of participants for an expense")
    @GetMapping("/{userId}/{expenseId}")
    public List<ExpenseParticipantResponse> getExpenseParticipants(@PathVariable Long userId, @PathVariable Long expenseId) {
        return expenseParticipantService.getExpenseParticipants(userId, expenseId);
    }

    @Operation(summary = "Get list of debt amounts per friend")
    @GetMapping("/{userId}/debts")
    public List<DebtResponse> getAllDebts(@PathVariable Long userId) {
        return expenseParticipantService.getAllDebts(userId);
    }

    @Operation(summary = "Get list of debts for a friend")
    @GetMapping("/{userId}/friends/{friendId}")
    public List<ExpenseParticipantResponse> getParticipation(@PathVariable Long userId, @PathVariable Long friendId) {
        return expenseParticipantService.getParticipation(userId, friendId);
    }

    @Operation(summary = "Get total debt amount of a friend")
    @GetMapping("/{userId}/friends/{friendId}/debts")
    public DebtResponse getDebt(@PathVariable Long userId, @PathVariable Long friendId) {
        return expenseParticipantService.getDebt(userId, friendId);
    }

    @Operation(summary = "Mark a participation as paid")
    @PatchMapping("/{userId}/{id}/paid")
    public void markAsPaid(@PathVariable Long userId, @PathVariable Long id) {
        expenseParticipantService.markAsPaid(userId, id);
    }

    @Operation(summary = "Mark a participation as unpaid")
    @PatchMapping("/{userId}/{id}/unpaid")
    public void markAsUnpaid(@PathVariable Long userId, @PathVariable Long id) {
        expenseParticipantService.markAsUnpaid(userId, id);
    }
}
