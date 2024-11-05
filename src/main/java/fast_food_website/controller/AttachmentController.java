package fast_food_website.controller;

import fast_food_website.entity.Attachment;
import fast_food_website.entity.AttachmentContent;
import fast_food_website.entity.Food;
import fast_food_website.repository.AttachmentContentRepository;
import fast_food_website.repository.AttachmentRepository;
import fast_food_website.repository.FoodRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@RestController
public class AttachmentController {

    AttachmentRepository attachmentRepository;
    AttachmentContentRepository attachmentContentRepository;
    FoodRepository foodRepository;

    @Autowired
    public AttachmentController(AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository, FoodRepository foodRepository) {
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.foodRepository = foodRepository;
    }


    @GetMapping("/image/{foodId}")
    public void getFileFromDb(@PathVariable("foodId") Long foodId, HttpServletResponse response) throws IOException {
        Optional<Food> foodOptional = foodRepository.findById(foodId);
        if (foodOptional.isPresent()) {
            Food food = foodOptional.get();
            Optional<Attachment> optionalAttachment = attachmentRepository.findById(food.getFile().getId());
            if (optionalAttachment.isPresent()) {
                Attachment attachment = optionalAttachment.get();
                Optional<AttachmentContent> contentOptional = attachmentContentRepository.findByAttachmentId(attachment.getId());
                if (contentOptional.isPresent()) {
                    AttachmentContent attachmentContent = contentOptional.get();
                    response.setContentType(attachment.getContentType());
                    response.setContentLength(attachmentContent.getBytes().length);
                    response.setHeader("Content-Disposition", "inline; filename=\"" + attachment.getFileOriginalName() + "\"");
                    response.getOutputStream().write(attachmentContent.getBytes());
                    response.getOutputStream().flush();
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}

