package project.dto.response;

import java.math.BigDecimal;

public record ExpenseParticipantResponse(
        Long id,
        BigDecimal shareAmount,
        Boolean paid,
        String friendName
) {}
