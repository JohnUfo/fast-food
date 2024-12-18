package fast_food_website.service;

import fast_food_website.payload.CategoryDto;
import jakarta.validation.Valid;
import org.springframework.ui.Model;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategories();

    String saveCategory(CategoryDto categoryDto, Model model);

    CategoryDto findCategoryById(long categoryId);

    String editCategory(@Valid CategoryDto categoryDto,Model model);

    void delete(long categoryId);

    List<CategoryDto> searchClubs(String query);
}
