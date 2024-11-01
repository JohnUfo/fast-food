package fast_food_website.controller;

import fast_food_website.entity.Category;
import fast_food_website.payload.CategoryDto;
import fast_food_website.service.CategoryService;
import fast_food_website.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/category/create")
    public String saveCategory(Model model) {
        userService.userForFrontEnd(model);
        model.addAttribute("category", new CategoryDto());
        return "category-create";
    }

    @PostMapping("/category/create")
    public String saveClub(@Valid @ModelAttribute("Category") CategoryDto categoryDto,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", categoryDto);
            return "category-create";
        }
        categoryService.saveCategory(categoryDto);
        return "redirect:/index";
    }
}
