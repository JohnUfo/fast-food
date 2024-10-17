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

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String getCategoryList(Model model) {
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "adminPage/categories";
    }

    @GetMapping("editCategoryPage/{id}")
    public String getEditPage(@PathVariable int id, Model model) {
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "adminPage/editCategory";
    }

    @GetMapping("addCategoryPage")
    public String getAddPage() {
        return "adminPage/addCategory";
    }

    @GetMapping("/{id}")
    public String getCategoryById(@PathVariable Integer id, Model model) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        List<Food> foodList = foodRepository.findByCategoryId(id);
        model.addAttribute("category", optionalCategory.get());
        model.addAttribute("foodList", foodList);
        return "adminPage/adminFoodsByCategoryId";
    }

    @PostMapping("/add")
    public String addCategory(@RequestParam String categoryName, Model model) {
        boolean exists = categoryRepository.existsByName(categoryName);
        if (exists) {
            model.addAttribute("messageResponse", new ApiResponse("Category is already exists", false));
            return "adminPage/addCategory";
        }
        categoryRepository.save(new Category(categoryName,null));
        model.addAttribute("messageResponse", new ApiResponse("Category added successfully", true));
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "adminPage/categories";
    }

    @PostMapping("/edit/{id}")
    public String editCategory(@PathVariable Integer id,
                               @RequestParam String categoryName, Model model) {
        ApiResponse apiResponse = categoryService.updateCategory(id, categoryName);
        model.addAttribute("messageResponse", apiResponse);
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "adminPage/categories";
    }

    @PostMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Integer id,
                                 @RequestParam(value = "_method", required = false) String method, Model model) {
        if ("DELETE".equalsIgnoreCase(method)) {
            boolean exists = categoryRepository.existsById(id);
            if (!exists) {
                model.addAttribute("messageResponse", new ApiResponse("Category not found", false));
            }
            categoryRepository.deleteById(id);
            model.addAttribute("categoryList", categoryRepository.findAll());
            model.addAttribute("messageResponse", new ApiResponse("Category deleted successfully", true));
            return "adminPage/categories";
        }
        return "adminPage/categories";
    }

    @GetMapping("/{id}/foods")
    public String getFoodsByCategory(@PathVariable Integer id, Model model) {
        List<Food> foodList = foodRepository.findByCategoryId(id);
        model.addAttribute("foodList", foodList);
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "adminPage/adminFoodsByCategoryId";
    }
}