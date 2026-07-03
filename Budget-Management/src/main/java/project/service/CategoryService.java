package project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.model.Category;
import project.model.User;
import project.repository.CategoryRepository;
import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final ValidationService validationService;
    private final CategoryRepository categoryRepository;

    public CategoryService(ValidationService validationService, CategoryRepository categoryRepository) {
        this.validationService = validationService;
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Long userId, String categoryName) {
        User user = validationService.getUserIfExists(userId);
        validationService.checkCategoryNameDuplication(userId, categoryName);
        Category category = new Category(categoryName, user);
        categoryRepository.save(category);
    }

    @Transactional(readOnly = true)
    public List<String> getUserCategories(Long userId) {
        validationService.getUserIfExists(userId);
        return categoryRepository.findByUserId(userId).stream()
                .map(Category::getName)
                .toList();
    }
}
