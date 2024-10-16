package fast_food_website.controller;

import fast_food_website.payload.ApiResponse;
import fast_food_website.payload.RegisterDto;
import fast_food_website.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class RegisterController {

    @Autowired
    AuthService authService;

    @GetMapping("/register")
    public String register(String errorMessage, Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam String password,
                           RedirectAttributes redirectAttributes) {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setFullName(fullName);
        registerDto.setEmail(email);
        registerDto.setPassword(password);

        ApiResponse apiResponse = authService.registerUser(registerDto);

        if (apiResponse.isSuccess()) {
            redirectAttributes.addAttribute("email", email);
            return "redirect:/auth/verify-email";
        }

        redirectAttributes.addAttribute("errorMessage", apiResponse.getMessage());
        return "redirect:/auth/register";
    }

    @PostMapping("/verifyEmail")
    public String verifyEmail(@RequestParam String email,
                              @RequestParam String verificationCode,
                              RedirectAttributes redirectAttributes) {
        ApiResponse apiResponse = authService.verifyEmail(verificationCode, email);
        if (apiResponse.isSuccess()) {
            return "login";
        } else {
            redirectAttributes.addAttribute("errorMessage", "Invalid email code. Please try again.");
            redirectAttributes.addAttribute("email", email);
            return "redirect:/auth/verify-email";
        }
    }

    @GetMapping("/verify-email")
    public String verifyEmailError(String errorMessage, String email, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("errorMessage", errorMessage);
        return "verify-email";
    }


}
