package project.dto.request;

import org.junit.jupiter.api.Test;
import project.exception.BadRequestException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateExpenseRequestTest {

    @Test
    void validate_should_pass_when_amount_valid() {
        CreateExpenseRequest req =
                new CreateExpenseRequest("food", 100.0, "desc", List.of(1L));

        assertDoesNotThrow(req::validate);
    }

    @Test
    void validate_should_throw_when_amount_null() {
        CreateExpenseRequest req =
                new CreateExpenseRequest("food", null, "desc", List.of(1L));

        assertThrows(BadRequestException.class, req::validate);
    }

    @Test
    void validate_should_throw_when_amount_negative() {
        CreateExpenseRequest req =
                new CreateExpenseRequest("food", -10.0, "desc", List.of(1L));

        assertThrows(BadRequestException.class, req::validate);
    }
}