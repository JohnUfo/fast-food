package fast_food_website.entity;

import fast_food_website.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Food extends AbsEntity {

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Attachment photo;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Double price;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public Food(String name, Double price, String description, Category category) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
    }
}
