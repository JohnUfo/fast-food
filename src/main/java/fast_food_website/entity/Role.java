package fast_food_website.entity;

import fast_food_website.entity.template.AbsEntity;
import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode(callSuper = true)
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends AbsEntity implements GrantedAuthority {

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
