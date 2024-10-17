package fast_food_website.repository;

import fast_food_website.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    boolean existsByName(String name);

    List<Food> findByCategoryId(Integer id);
}
