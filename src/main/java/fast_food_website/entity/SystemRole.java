package fast_food_website.entity;

import fast_food_website.entity.enums.SystemRoleName;
import fast_food_website.entity.template.AbsEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SystemRole extends AbsEntity implements GrantedAuthority {

    @Enumerated(EnumType.STRING)
    private SystemRoleName systemRoleName;

    @Override
    public String getAuthority() {
        return systemRoleName.name();
    }
}


