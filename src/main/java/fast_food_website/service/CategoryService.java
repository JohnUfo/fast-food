package fast_food_website.service;

import fast_food_website.payload.CategoryDto;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategories();

    void saveCategory(@Valid CategoryDto categoryDto);
}
