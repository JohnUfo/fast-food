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
public class Food extends AbsEntity {

    @Column(nullable = false, unique = true)
    private String name;

    private Double price;

    private String description;

    private int count;

    @ManyToOne
    private Category category;
}
