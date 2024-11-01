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
import org.springframework.web.bind.annotation.*;

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
    public String saveClub(@Valid @ModelAttribute("category") CategoryDto categoryDto,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("category", categoryDto);
            return "category-create";
        }
        return categoryService.saveCategory(categoryDto,model);
    }

    @GetMapping("/category/{categoryId}/edit")
    public String editClubForm(@PathVariable("categoryId") long categoryId, Model model) {
        model.addAttribute("category", categoryService.findCategoryById(categoryId));
        return "category-edit";
    }

    @PostMapping("/category/{categoryId}/edit")
    public String editClub(@PathVariable("categoryId") long categoryId,
                           @Valid @ModelAttribute("category") CategoryDto categoryDto,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "category-edit";
        }
        categoryDto.setId(categoryId);
        categoryService.editCategory(categoryDto);
        return "redirect:/";
    }

    @GetMapping("/category/{categoryId}/delete")
    public String deleteClub(@PathVariable("categoryId") long categoryId) {
        categoryService.delete(categoryId);
        return "redirect:/";
    }

    @GetMapping("/category/search")
    public String searchClub(@RequestParam(value = "query") String query, Model model) {
        model.addAttribute("category", categoryService.searchClubs(query));
        return "redirect:/";
    }
}
