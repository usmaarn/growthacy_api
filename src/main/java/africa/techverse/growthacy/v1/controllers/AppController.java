package africa.techverse.growthacy.v1.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController("/")
public class AppController {
    @GetMapping("/")
    public Map<String, String> index() {
        return Map.of(
                "message", "Growthacy API",
                "version", "1.0.0"
        );
    }
}
