package project.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import project.service.CategoryService;
import project.service.UserService;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Test
    void createCategory_shouldPersist() {

        String username = "ali-" + UUID.randomUUID();

        Long userId = userService.createUser(username);

        categoryService.createCategory(userId, "food");

        List<String> categories = categoryService.getUserCategories(userId);

        assertEquals(1, categories.size());
        assertEquals("food", categories.get(0));
    }
}