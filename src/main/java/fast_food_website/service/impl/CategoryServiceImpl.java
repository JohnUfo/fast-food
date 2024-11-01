package fast_food_website.service.impl;


import fast_food_website.entity.Category;
import fast_food_website.payload.CategoryDto;
import fast_food_website.repository.CategoryRepository;
import fast_food_website.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;
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

    @Override
    public String saveCategory(CategoryDto categoryDto, Model model) {
        if (categoryRepository.existsByName(categoryDto.getName())){
            model.addAttribute("message", "Category already exists");
            return "category-create";
        }
        categoryRepository.save(mapToCategory(categoryDto));
        return "redirect:/";
    }

    @Override
    public CategoryDto findCategoryById(long categoryId) {
        return categoryRepository.findById(categoryId).map(this::mapToCategoryDto).get();
    }

    @Override
    public void editCategory(CategoryDto categoryDto) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryDto.getId());
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
        }
    }

    private Category mapToCategory(CategoryDto categoryDto) {
        return new Category(categoryDto.getName(),categoryDto.getFoods());
    }

    private CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .foods(category.getFoods())
                .build();
    }

    @Override
    public void delete(long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryDto> searchClubs(String query) {
        return categoryRepository.searchCategories(query).stream().map(this::mapToCategoryDto).toList();
    }
}
