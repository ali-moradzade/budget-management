package project.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import project.model.Category;
import project.model.User;
import project.repository.CategoryRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    ValidationService validationService;

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @Test
    void createCategory_shouldSave() {

        User user = new User("ali");
        user.setId(1L);

        when(validationService.getUserIfExists(1L)).thenReturn(user);

        categoryService.createCategory(1L, "food");

        verify(validationService).checkCategoryNameDuplication(1L, "food");
        verify(categoryRepository).save(any(Category.class));
    }

    @Test
    void getUserCategories_shouldReturnNames() {

        User user = new User("ali");
        user.setId(1L);

        Category c = new Category("food", user);

        when(validationService.getUserIfExists(1L)).thenReturn(user);
        when(categoryRepository.findByUserId(1L)).thenReturn(List.of(c));

        var result = categoryService.getUserCategories(1L);

        assertEquals(1, result.size());
        assertEquals("food", result.get(0));
    }
}