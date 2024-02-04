package com.BetterTfrrsWebsite;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import DB.DB;

@RestController
public class HelloController {
    DB db = new DB();
    @GetMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
