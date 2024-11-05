package fast_food_website.service.impl;


import fast_food_website.entity.Attachment;
import fast_food_website.entity.AttachmentContent;
import fast_food_website.entity.Food;
import fast_food_website.payload.FoodDto;
import fast_food_website.repository.AttachmentRepository;
import fast_food_website.repository.CategoryRepository;
import fast_food_website.repository.FoodRepository;
import fast_food_website.service.FoodService;
import jakarta.transaction.Transactional;
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
        return foodRepository.findByCategoryId(categoryId).stream().map(this::mapToFoodDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String saveFood(FoodDto foodDto, MultipartFile file, Model model, Long categoryId) throws IOException {
        boolean existsByName = foodRepository.existsByName(foodDto.getName());
        if (existsByName) {
            model.addAttribute("message","Food already exists");
            return "food-create";
        }

        Attachment attachment = null;

        if (file != null && !file.isEmpty()) {
            attachment = new Attachment();
            attachment.setFileOriginalName(file.getOriginalFilename());
            attachment.setSize(file.getSize());
            attachment.setContentType(file.getContentType());
            attachment.setName("uniqueFileName_" + System.currentTimeMillis());

            AttachmentContent attachmentContent = new AttachmentContent();
            attachmentContent.setBytes(file.getBytes());
            attachmentContent.setAttachment(attachment);
            attachment.setAttachmentContent(attachmentContent);
            attachment = attachmentRepository.save(attachment);
        }

        Food food = new Food();
        food.setName(foodDto.getName());
        food.setPrice(foodDto.getPrice());
        food.setDescription(foodDto.getDescription());
        food.setFile(attachment);
        food.setCategory(categoryRepository.findById(categoryId).orElseThrow());

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
                .attachment(food.getFile())
                .build();
    }
}
