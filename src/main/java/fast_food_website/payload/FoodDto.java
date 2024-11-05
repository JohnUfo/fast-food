package fast_food_website.payload;

import fast_food_website.entity.Attachment;
import fast_food_website.entity.Category;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {
    private Long id;
    private MultipartFile photo;
    private Attachment attachment;
    @NotNull(message = "Name should not be empty")
    private String name;
    @NotNull(message = "Price should not be empty")
    private Double price;
    @NotNull(message = "Description should not be empty")
    private String description;
    private Category category;

}
