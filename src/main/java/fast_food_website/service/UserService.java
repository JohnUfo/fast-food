package fast_food_website.service;

import fast_food_website.entity.User;
import fast_food_website.payload.EmailVerifyDto;
import fast_food_website.payload.RegistrationDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.ui.Model;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);

    User findByEmail(@NotEmpty(message = "Email should not be empty") String email);

    void userForFrontEnd(Model model);

    void verifyEmail(@Valid EmailVerifyDto emailVerifyDto,Model model);
}