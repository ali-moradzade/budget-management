package project.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record ExpenseResponse(
        Long id,
        BigDecimal amount,
        String description,
        LocalDate expenseDate,
        String category,
        List<String> participants
) {}
