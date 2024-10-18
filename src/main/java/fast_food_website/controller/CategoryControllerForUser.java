package fast_food_website.controller;

import fast_food_website.entity.Category;
import fast_food_website.entity.Food;
import fast_food_website.payload.ApiResponse;
import fast_food_website.repository.CategoryRepository;
import fast_food_website.repository.FoodRepository;
import fast_food_website.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/category")
public class CategoryControllerForUser {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String getCategoryList(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "categories";
    }

    @GetMapping("/{id}/foods")
    public String getFoodsByCategory(@PathVariable Integer id, Model model) {
        List<Food> foodList = foodRepository.findByCategoryId(id);
        model.addAttribute("foodList", foodList);
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "foodsByCategoryId";
    }
}