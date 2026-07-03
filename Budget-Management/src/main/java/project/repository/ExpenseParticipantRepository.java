package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.ExpenseParticipant;
import java.util.List;

public interface ExpenseParticipantRepository extends JpaRepository<ExpenseParticipant, Long> {

    List<ExpenseParticipant> findByExpenseId(Long expenseId);

    List<ExpenseParticipant> findByFriendId(Long friendId);

    List<ExpenseParticipant> findByFriendIdAndPaidFalse(Long friendId);
}
