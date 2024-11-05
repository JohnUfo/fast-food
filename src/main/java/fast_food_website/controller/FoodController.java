package fast_food_website.controller;

import fast_food_website.payload.FoodDto;
import fast_food_website.service.FoodService;
import fast_food_website.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class FoodController {

    private final FoodService foodService;
    private UserService userService;

    @Autowired
    public FoodController(FoodService foodService, UserService userService) {
        this.foodService = foodService;
        this.userService = userService;
    }

    @GetMapping("/food/{categoryId}")
    public String saveCategory(@PathVariable("categoryId") Long categoryId, Model model) {
        userService.userForFrontEnd(model);
        model.addAttribute("foods", foodService.getFoodsByCategoryId(categoryId));
        return "food-list";
    }

    @GetMapping("/food/{categoryId}/create")
    public String createCategory(Model model) {
        userService.userForFrontEnd(model);
        model.addAttribute("food", new FoodDto());
        return "food-create";
    }

    @PostMapping("/food/{categoryId}/create")
    public String saveFood(@PathVariable("categoryId") Long categoryId,
                           @Valid @ModelAttribute("food") FoodDto foodDto,
                           BindingResult result,
                           Model model) throws IOException {

        if (result.hasErrors()) {
            model.addAttribute("categoryId", categoryId);
            model.addAttribute("food", foodDto);
            return "food-create";
        }
        return foodService.saveFood(foodDto, foodDto.getPhoto(), model, categoryId);
    }
}
