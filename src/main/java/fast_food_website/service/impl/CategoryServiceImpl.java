package fast_food_website.service.impl;


import fast_food_website.entity.Category;
import fast_food_website.payload.CategoryDto;
import fast_food_website.repository.CategoryRepository;
import fast_food_website.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream().map(this::mapToCategoryDto).collect(Collectors.toList());
    }

    private CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .foods(category.getFoods())
                .build();
    }
}
