package project.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import project.exception.BadRequestException;
import project.service.CategoryService;
import java.util.List;

@Tag(name = "Categories", description = "Category management endpoints")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Create a new category for your expenses")
    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@PathVariable Long userId, @RequestParam String categoryName) {
        if (categoryName.isBlank()) {
            throw new BadRequestException("Category name can not be empty");
        }
        categoryService.createCategory(userId, categoryName);
    }

    @Operation(summary = "Get list of your categories")
    @GetMapping("/{userId}")
    public List<String> getUserCategories(@PathVariable Long userId) {
        return categoryService.getUserCategories(userId);
    }
}
