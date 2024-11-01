package fast_food_website.payload;


import fast_food_website.entity.Basket;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    private Long id;

    @NotEmpty(message = "Full name should not be empty")
    private String fullName;

    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    private String verificationCode;

    private Basket basket;
}
