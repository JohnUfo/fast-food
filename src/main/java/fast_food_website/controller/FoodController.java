package fast_food_website.controller;

import fast_food_website.entity.Category;
import fast_food_website.entity.Food;
import fast_food_website.payload.ApiResponse;
import fast_food_website.repository.CategoryRepository;
import fast_food_website.repository.FoodRepository;
import fast_food_website.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/food")
public class FoodController {

    @Autowired
    FoodService foodService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FoodRepository foodRepository;


    @GetMapping("editFoodPage/{id}")
    public String getEditPage(@PathVariable int id, Model model) {
        model.addAttribute("food", foodRepository.findById(id).get());
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "adminPage/editFood";
    }

    @PostMapping("/edit/{foodId}")
    public String editCategory(@PathVariable Integer foodId,
                               @RequestParam String foodName,
                               @RequestParam double foodPrice,
                               @RequestParam String foodDescription,
                               @RequestParam Integer categoryId, Model model) {
        Food food = new Food(foodName, foodPrice, foodDescription, categoryRepository.findById(categoryId).get());
        ApiResponse apiResponse = foodService.updateFood(foodId, food);
        model.addAttribute("messageResponse", apiResponse);
        model.addAttribute("foodList", foodRepository.findById(categoryId));
        return "redirect:/admin/category/" + categoryId + "/foods";
    }


    @GetMapping("/addFoodPage")
    public String addFoodPage(@RequestParam Integer categoryId, Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        model.addAttribute("categoryId", categoryId);
        return "adminPage/addFood";
    }

    @PostMapping("/add")
    public String addFood(@RequestParam String foodName,
                          @RequestParam double foodPrice,
                          @RequestParam String foodDescription,
                          @RequestParam Integer categoryId,
                          Model model) {

        boolean success = foodService.addFood(foodName, foodPrice, foodDescription, categoryId);
        if (success) {
            model.addAttribute("messageResponse", new ApiResponse("Food added successfully!", true));
        } else {
            model.addAttribute("messageResponse", new ApiResponse("Failed to add food", false));
        }

        return "redirect:/admin/category/" + categoryId + "/foods";
    }


    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id,
                                 @RequestParam Integer categoryId,
                                 @RequestParam(value = "_method", required = false) String method, Model model) {
        if ("DELETE".equalsIgnoreCase(method)) {
            boolean exists = foodRepository.existsById(id);
            if (!exists) {
                model.addAttribute("messageResponse", new ApiResponse("Food not found", false));
            }
            foodRepository.deleteById(id);
            model.addAttribute("categoryId", id);
            model.addAttribute("categoryList", foodRepository.findByCategoryId(categoryId));
            model.addAttribute("messageResponse", new ApiResponse("Food deleted successfully", true));
            return "adminPage/categories";
        }
        return "adminPage/categories";
    }
}


