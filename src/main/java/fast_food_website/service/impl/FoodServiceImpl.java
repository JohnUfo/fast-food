package fast_food_website.service.impl;


import fast_food_website.entity.Attachment;
import fast_food_website.entity.Category;
import fast_food_website.entity.Food;
import fast_food_website.payload.FoodDto;
import fast_food_website.repository.AttachmentRepository;
import fast_food_website.repository.CategoryRepository;
import fast_food_website.repository.FoodRepository;
import fast_food_website.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class FoodServiceImpl implements FoodService {

    private final FoodRepository foodRepository;
    private final AttachmentRepository attachmentRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public FoodServiceImpl(FoodRepository foodRepository, AttachmentRepository attachmentRepository, CategoryRepository categoryRepository) {
        this.foodRepository = foodRepository;
        this.attachmentRepository = attachmentRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<FoodDto> getFoodsByCategoryId(Long categoryId) {
        return foodRepository.findByCategoryId(categoryId).stream().map(food -> mapToFoodDto(food)).collect(Collectors.toList());
    }

    @Override
    public String saveFood(FoodDto foodDto, MultipartFile photo, Model model, Long categoryId) throws IOException {
        String fileName = photo.getOriginalFilename();
        String fileType = photo.getContentType();
        byte[] data = photo.getBytes();

        Attachment attachment = new Attachment(null,fileName, fileType, data);
        attachmentRepository.save(attachment);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Food food = new Food(foodDto.getName(), foodDto.getPrice(), foodDto.getDescription(), category);
        food.setPhoto(attachment);

        foodRepository.save(food);
        return "redirect:/food/" + food.getCategory().getId();
    }

    private FoodDto mapToFoodDto(Food food) {
        return FoodDto.builder()
                .id(food.getId())
                .name(food.getName())
                .price(food.getPrice())
                .description(food.getDescription())
                .category(food.getCategory())
                .photo(food.getPhoto())
                .build();
    }
}
