package fast_food_website.payload;

import fast_food_website.entity.Attachment;
import fast_food_website.entity.Category;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodDto {
    private Long id;
    private Attachment photo;
    private String name;
    private Double price;
    private String description;
    private Category category;

}
