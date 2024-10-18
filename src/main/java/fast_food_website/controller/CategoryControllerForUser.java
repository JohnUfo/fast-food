package fast_food_website.controller;

import fast_food_website.entity.Category;
import fast_food_website.entity.Food;
import fast_food_website.repository.CategoryRepository;
import fast_food_website.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryControllerForUser {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FoodRepository foodRepository;

    @GetMapping
    public String home(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categoryList", categories);

        // Load foods for the default category if available
        if (!categories.isEmpty()) {
            List<Food> defaultFoodList = foodRepository.findAllByCategoryId(categories.get(0).getId());
            model.addAttribute("selectedFoods", defaultFoodList);
        }

        return "menuPage"; // Ensure this matches your HTML file name
    }

    @GetMapping("/{id}")
    public String getFoodsByCategory(@PathVariable Integer id, Model model) {
        List<Category> categories = categoryRepository.findAll();
        List<Food> foods = foodRepository.findAllByCategoryId(id);

        model.addAttribute("categoryList", categories);
        model.addAttribute("selectedFoods", foods);

        return "menuPage"; // Ensure this matches your HTML file name
    }
}
