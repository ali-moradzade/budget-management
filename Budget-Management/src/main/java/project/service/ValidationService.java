package project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.exception.ResourceAlreadyExistsException;
import project.exception.ResourceNotFoundException;
import project.model.*;
import project.repository.*;

import java.util.List;

@Service
@Transactional
public class ValidationService {

    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseParticipantRepository expenseParticipantRepository;
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public ValidationService(
            CategoryRepository categoryRepository,
            ExpenseRepository expenseRepository,
            ExpenseParticipantRepository expenseParticipantRepository,
            FriendRepository friendRepository,
            UserRepository userRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
        this.expenseParticipantRepository = expenseParticipantRepository;
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    Category getCategoryIfExists(Long userId, String categoryName) {
        if (categoryName != null) {
            return categoryRepository.findCategoryByUserIdAndName(userId, categoryName)
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with name: " + categoryName));
        }
        return null;
    }

    Expense getExpenseIfExists(Long userId, Long expenseId) {
        return expenseRepository.findExpenseByUserIdAndId(userId, expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + expenseId));
    }

    ExpenseParticipant getExpenseParticipantIfExists(Long userId, Long id) {
        ExpenseParticipant participant = expenseParticipantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense participant not found with id: " + id));
        expenseRepository.findExpenseByUserIdAndId(userId, participant.getExpense().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Expense participant not found with id: " + id));
        return participant;
    }

    Friend getFriendIfExists(Long userId, Long friendId) {
        return friendRepository.findFriendByUserIdAndId(userId, friendId)
                .orElseThrow(() -> new ResourceNotFoundException("Friend not found with id: " + friendId));
    }

    User getUserIfExists(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    List<Friend> getAllFriends(Long userId) {
        return friendRepository.findByUserId(userId);
    }

    void checkCategoryNameDuplication(Long userId, String categoryName) {
        if (categoryRepository.findCategoryByUserIdAndName(userId, categoryName).isPresent()) {
            throw new ResourceAlreadyExistsException("User already created category with name: " + categoryName);
        }
    }

    void checkFriendNameDuplication(Long userId, String friendName) {
        if (friendRepository.findFriendByUserIdAndFriendName(userId, friendName).isPresent()) {
            throw new ResourceAlreadyExistsException("User already added friend with name: " + friendName);
        }
    }

    void checkUserNameDuplication(String username) {
        if (userRepository.findUserByUsername(username).isPresent()) {
            throw new ResourceAlreadyExistsException("User already exists with username: " + username);
        }
    }
}
