package fast_food_website.controller;

import fast_food_website.entity.User;
import fast_food_website.payload.CategoryDto;
import fast_food_website.payload.EmailVerifyDto;
import fast_food_website.payload.LoginDto;
import fast_food_website.payload.RegistrationDto;
import fast_food_website.service.CategoryService;
import fast_food_website.service.UserService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AuthController {

    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    public AuthController(UserService userService, CategoryService categoryService) {
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping("/register")
    public String getRegisterForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }

    @SneakyThrows
    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user") RegistrationDto user,
                           BindingResult result,
                           Model model) {

        User existingUserEmail = userService.findByEmail(user.getEmail());

        if (existingUserEmail != null && existingUserEmail.getEmail() != null && !existingUserEmail.getEmail().isEmpty()) {
            return "redirect:/register?fail";
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }

        userService.saveUser(user);
        model.addAttribute("user", new EmailVerifyDto());
        return "verify-email";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("user", new LoginDto());
        return "login";
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@Valid @ModelAttribute("user") EmailVerifyDto emailVerifyDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("user", emailVerifyDto);
            return "verify-email";
        }

        userService.verifyEmail(emailVerifyDto, model);
        return "redirect:/index?success";
    }

    @GetMapping("/index")
    public String categoryList(Model model) {
        userService.userForFrontEnd(model);
        List<CategoryDto> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        return "index";
    }
}
