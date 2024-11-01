package fast_food_website.repository;

import fast_food_website.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.name like concat('%',:query,'%')")
    List<Category> searchCategories(String query);

    boolean existsByName(String name);
}
