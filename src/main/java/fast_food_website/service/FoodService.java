package fast_food_website.service;

import fast_food_website.payload.FoodDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface FoodService {

    List<FoodDto> getFoodsByCategoryId(Long categoryId);

    String saveFood(FoodDto foodDto, MultipartFile photo, Model model, Long categoryId) throws IOException;
}
