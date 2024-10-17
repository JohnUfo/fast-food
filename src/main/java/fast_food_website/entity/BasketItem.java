package fast_food_website.entity;

import fast_food_website.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BasketItem extends AbsEntity {

    @ManyToOne
    private Food food;

    private int quantity;

    @ManyToOne
    private Basket basket;
}
