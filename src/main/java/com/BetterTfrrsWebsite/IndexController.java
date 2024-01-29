package com.BetterTfrrsWebsite;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping(value = "/index")
    public String index(Model model){
        //model.addAttribute("name", name);
        return "index";
    }
}
