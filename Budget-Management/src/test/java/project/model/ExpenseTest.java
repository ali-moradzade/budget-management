package project.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void expense_should_store_values_correctly() {
        Expense expense = new Expense();

        expense.setAmount(new BigDecimal("100.50"));
        expense.setDescription("Lunch");

        assertEquals(new BigDecimal("100.50"), expense.getAmount());
        assertEquals("Lunch", expense.getDescription());
    }
}