package fast_food_website.repository;

import fast_food_website.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);

    Category getByName(String name);
}
