package project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.dto.response.DebtResponse;
import project.dto.response.ExpenseParticipantResponse;
import project.model.ExpenseParticipant;
import project.model.Friend;
import project.repository.ExpenseParticipantRepository;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class ExpenseParticipantService {

    private final ValidationService validationService;
    private final ExpenseParticipantRepository expenseParticipantRepository;

    public ExpenseParticipantService(
            ValidationService validationService,
            ExpenseParticipantRepository expenseParticipantRepository
    ) {
        this.validationService = validationService;
        this.expenseParticipantRepository = expenseParticipantRepository;
    }

    @Transactional(readOnly = true)

    public List<ExpenseParticipantResponse> getExpenseParticipants(Long userId, Long expenseId) {
        validationService.getUserIfExists(userId);
        validationService.getExpenseIfExists(userId, expenseId);
        return expenseParticipantRepository.findByExpenseId(expenseId).stream()
                .map(ExpenseParticipant::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DebtResponse> getAllDebts(Long userId) {
        validationService.getUserIfExists(userId);
        List<Friend> friends = validationService.getAllFriends(userId);
        return friends.stream()
                .map(Friend::getId)
                .map(friendId -> getDebt(userId, friendId))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ExpenseParticipantResponse> getParticipation(Long userId, Long friendId) {
        validationService.getUserIfExists(userId);
        validationService.getFriendIfExists(userId, friendId);
        return expenseParticipantRepository.findByFriendId(friendId).stream()
                .map(ExpenseParticipant::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public DebtResponse getDebt(Long userId, Long friendId) {
        validationService.getUserIfExists(userId);
        Friend friend = validationService.getFriendIfExists(userId, friendId);
        List<Double> debts =  expenseParticipantRepository.findByFriendIdAndPaidFalse(friendId).stream()
                .map(ExpenseParticipant::getShareAmount)
                .map(BigDecimal::doubleValue)
                .toList();
        return new DebtResponse(friend.getFriendName(), debts.stream().reduce(0.0, Double::sum));
    }

    public void markAsPaid(Long userId, Long id) {
        validationService.getUserIfExists(userId);
        ExpenseParticipant participant = validationService.getExpenseParticipantIfExists(userId, id);
        participant.setPaid(true);
        expenseParticipantRepository.save(participant);
    }

    public void markAsUnpaid(Long userId, Long id) {
        validationService.getUserIfExists(userId);
        ExpenseParticipant participant = validationService.getExpenseParticipantIfExists(userId, id);
        participant.setPaid(false);
        expenseParticipantRepository.save(participant);
    }
}
