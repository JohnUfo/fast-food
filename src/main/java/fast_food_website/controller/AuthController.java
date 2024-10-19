package fast_food_website.controller;

import fast_food_website.entity.User;
import fast_food_website.payload.ApiResponse;
import fast_food_website.payload.RegisterDto;
import fast_food_website.security.JwtProvider;
import fast_food_website.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/auth")
public class AuthController {

    AuthService authService;
    AuthenticationManager authenticationManager;
    JwtProvider jwtProvider;

    @Autowired
    @Lazy
    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> getApiData(@RequestParam String messageResponse) {
        Map<String, Object> data = new HashMap<>();
        data.put("messageResponse", messageResponse);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpServletRequest request,
                        RedirectAttributes redirectAttributes) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            User user = (User) authenticate.getPrincipal();

            String token = jwtProvider.generateToken(user.getEmail());

            HttpSession session = request.getSession();
            session.setAttribute("JWT_TOKEN", token);
            session.setAttribute("USER", user);
            System.out.println(user);

            return "redirect:/";
        } catch (Exception e) {
            redirectAttributes.addAttribute("messageResponse", "Invalid email or password");
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String register(String messageResponse, Model model) {
        model.addAttribute("messageResponse", messageResponse);
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

        redirectAttributes.addAttribute("messageResponse", apiResponse.getMessage());
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
            redirectAttributes.addAttribute("messageResponse", "Invalid email code. Please try again.");
            redirectAttributes.addAttribute("email", email);
            return "redirect:/auth/verify-email";
        }
    }

    @GetMapping("/verify-email")
    public String verifyEmailError(String messageResponse, String email, Model model) {
        model.addAttribute("email", email);
        model.addAttribute("messageResponse", messageResponse);
        return "verify-email";
    }

}
