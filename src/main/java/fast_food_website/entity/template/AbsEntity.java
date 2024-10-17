package fast_food_website.entity.template;


import jakarta.persistence.*;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class AbsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
}
