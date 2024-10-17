package fast_food_website.service;

import fast_food_website.entity.Food;
import fast_food_website.payload.ApiResponse;
import fast_food_website.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {

    @Autowired
    FoodRepository foodRepository;

    public ApiResponse updateFood(Integer id, Food updatedFood) {
        boolean existsById = foodRepository.existsById(id);
        if (!existsById) {
            return new ApiResponse("Food is not exist", false);
        }
        Food food = foodRepository.findById(id).get();
        food.setName(updatedFood.getName());
        food.setDescription(updatedFood.getDescription());
        food.setPrice(updatedFood.getPrice());
        food.setCategory(updatedFood.getCategory());
        foodRepository.save(food);
        return new ApiResponse("Food updated successfully", true);
    }
}
