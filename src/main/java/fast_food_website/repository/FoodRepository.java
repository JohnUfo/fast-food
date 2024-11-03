package fast_food_website.repository;

import fast_food_website.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findByCategoryId(Long categoryId);

}
