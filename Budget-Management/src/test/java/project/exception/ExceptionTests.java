package project.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTests {

    @Test
    void badRequestException_should_store_message() {
        BadRequestException ex = new BadRequestException("bad request");
        assertEquals("bad request", ex.getMessage());
    }

    @Test
    void resourceNotFoundException_should_store_message() {
        ResourceNotFoundException ex = new ResourceNotFoundException("not found");
        assertEquals("not found", ex.getMessage());
    }

    @Test
    void resourceAlreadyExistsException_should_store_message() {
        ResourceAlreadyExistsException ex = new ResourceAlreadyExistsException("exists");
        assertEquals("exists", ex.getMessage());
    }
}