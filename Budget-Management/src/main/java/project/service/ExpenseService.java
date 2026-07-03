package project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.request.CreateExpenseRequest;
import project.dto.response.ExpenseResponse;
import project.model.*;
import project.repository.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExpenseService {

    private final ValidationService validationService;
    private final ExpenseRepository expenseRepository;
    private final ExpenseParticipantRepository expenseParticipantRepository;

    public ExpenseService(
            ValidationService validationService,
            ExpenseRepository expenseRepository,
            ExpenseParticipantRepository expenseParticipantRepository
    ) {
        this.validationService = validationService;
        this.expenseRepository = expenseRepository;
        this.expenseParticipantRepository = expenseParticipantRepository;
    }

    public void createExpense(Long userId, CreateExpenseRequest request) {
        User user = validationService.getUserIfExists(userId);
        Category category = validationService.getCategoryIfExists(userId, request.category());

        List<Friend> friends = new ArrayList<>();
        if (request.participants() != null) {
            for (Long participantId : request.participants()) {
                friends.add(validationService.getFriendIfExists(userId, participantId));
            }
        }

        Expense expense = new Expense(request.amount(), request.description(), LocalDate.now(), user, category);
        expenseRepository.save(expense);

        if (!friends.isEmpty()) {
            int participantsCount = friends.size() + 1;
            Double shareAmount = request.amount() / participantsCount;

            for (Friend friend : friends) {
                ExpenseParticipant participant = new ExpenseParticipant(shareAmount, false, expense, friend);
                expenseParticipantRepository.save(participant);
            }
        }
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> getUserExpenses(Long userId) {
        validationService.getUserIfExists(userId);
        return expenseRepository.findByUserId(userId).stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ExpenseResponse getExpense(Long userId, Long expenseId) {
        validationService.getUserIfExists(userId);
        return toDto(validationService.getExpenseIfExists(userId, expenseId));
    }

    private ExpenseResponse toDto(Expense expense) {
        List<String> participants = expenseParticipantRepository.findByExpenseId(expense.getId()).stream()
                .map(ExpenseParticipant::getFriend)
                .map(Friend::getFriendName)
                .toList();

        return new ExpenseResponse(
                expense.getId(),
                expense.getAmount(),
                expense.getDescription(),
                expense.getExpenseDate(),
                expense.getCategory().getName(),
                participants);
    }
}
