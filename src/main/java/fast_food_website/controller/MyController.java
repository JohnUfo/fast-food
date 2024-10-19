package fast_food_website.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MyController {

    // Return JSON content (API response)
    @GetMapping("/api/data")
    public ResponseEntity<Map<String, String>> getApiData() {
        Map<String, String> data = new HashMap<>();
        data.put("message", "HELLO MY NAME IS UMIDJON!");
        data.put("status", "success");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}