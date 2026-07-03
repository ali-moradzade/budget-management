package project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.model.Expense;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long userId);

    Optional<Expense> findExpenseByUserIdAndId(Long userId, Long id);
}
