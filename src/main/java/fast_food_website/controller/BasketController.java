package fast_food_website.controller;

import fast_food_website.entity.User;
import fast_food_website.security.CurrentUser;
import fast_food_website.service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/basket")
public class BasketController {

    @Autowired
    private BasketService basketService;

    @PostMapping("/add")
    public ResponseEntity<String> addToBasket(@RequestParam Long foodId,
                                              @RequestParam int quantity,
                                              @CurrentUser User user) {
        basketService.addToBasket(user, foodId, quantity);
        return ResponseEntity.ok("Food added to basket");
    }
}

