package fast_food_website.controller;

import fast_food_website.entity.Food;
import fast_food_website.payload.ApiResponse;
import fast_food_website.repository.FoodRepository;
import fast_food_website.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.Integer;
import java.util.Optional;

@Controller
@RequestMapping("/food")
public class FoodController {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FoodService foodService;

    @GetMapping
    public String getFoodList(RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("foodList", foodRepository.findAll());
        return "/";//change return path
    }

    @GetMapping("/{id}")
    public String getFoodById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Optional<Food> optionalFood = foodRepository.findById(id);
        redirectAttributes.addAttribute("food", optionalFood.get());
        return "/";//change return path
    }

    @PutMapping("/{id}")
    public String updateFood(@PathVariable Integer id, @RequestBody Food food, RedirectAttributes redirectAttributes) {
        ApiResponse apiResponse = foodService.updateFood(id, food);
        redirectAttributes.addAttribute("apiResponse", apiResponse);
        return "/";//change return path
    }

    @PostMapping
    public String addFood(@RequestBody Food food, RedirectAttributes redirectAttributes) {
        boolean exists = foodRepository.existsByName(food.getName());
        if (exists) {
            redirectAttributes.addAttribute("messageResponse", new ApiResponse("Food is already exists", false));
            return "redirect:/";//change return path 
        }
        foodRepository.save(food);
        redirectAttributes.addAttribute("messageResponse", new ApiResponse("Food added successfully", true));
        return "redirect:/";
    }
}
