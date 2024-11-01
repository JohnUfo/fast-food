package fast_food_website.payload;

import fast_food_website.entity.Food;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private Long id;

    @NotEmpty(message = "Category name should not be empty")
    private String name;

    private List<Food> foods;
}
