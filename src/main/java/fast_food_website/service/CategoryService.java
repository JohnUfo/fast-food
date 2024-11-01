package fast_food_website.service;

import fast_food_website.payload.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAllCategories();
}
