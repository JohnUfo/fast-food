package fast_food_website.payload;


import fast_food_website.entity.Basket;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerifyDto {

    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Verification code should not be empty")
    private String verificationCode;

}
