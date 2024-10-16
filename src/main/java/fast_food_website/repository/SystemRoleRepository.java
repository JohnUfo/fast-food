package fast_food_website.repository;

import fast_food_website.entity.SystemRole;
import fast_food_website.entity.enums.SystemRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SystemRoleRepository extends JpaRepository<SystemRole, UUID> {
    SystemRole findBySystemRoleName(SystemRoleName systemRoleName);
}
