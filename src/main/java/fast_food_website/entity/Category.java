package fast_food_website.entity;

import fast_food_website.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
public class Category extends AbsEntity {

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Food> foods;
}
