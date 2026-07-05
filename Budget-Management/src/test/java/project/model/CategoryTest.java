package project.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void category_should_store_values_correctly() {
        Category category = new Category();

        category.setId(1L);
        category.setName("Food");

        assertEquals(1L, category.getId());
        assertEquals("Food", category.getName());
    }
}