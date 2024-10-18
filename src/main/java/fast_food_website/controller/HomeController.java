package fast_food_website.controller;

import fast_food_website.entity.Category;
import fast_food_website.entity.Food;
import fast_food_website.repository.CategoryRepository;
import fast_food_website.repository.FoodRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private FoodRepository foodRepository;

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("JWT_TOKEN") != null) {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("categoryList", categories);
            return "menuPage";
        }
        return "index";
    }
}
