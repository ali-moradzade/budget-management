package project.dto.request;

import project.exception.BadRequestException;
import java.util.List;

public record CreateExpenseRequest(
        String category,
        Double amount,
        String description,
        List<Long> participants
) {
    public void validate() {
        if (amount == null || amount <= 0) {
            throw new BadRequestException("Amount can not be empty and also must be a positive number");
        }
    }
}