package fast_food_website.entity;

import fast_food_website.entity.enums.SystemRoleName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SystemRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private SystemRoleName systemRoleName;

    @Override
    public String getAuthority() {
        return systemRoleName.name();
    }
}


