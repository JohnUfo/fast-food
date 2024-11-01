package fast_food_website.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fast_food_website.entity.template.AbsEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BasketItem extends AbsEntity {

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    @JsonIgnore
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;

    private Long quantity;
}
