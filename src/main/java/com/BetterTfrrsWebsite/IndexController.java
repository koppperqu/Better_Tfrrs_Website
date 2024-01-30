package com.BetterTfrrsWebsite;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {
    @GetMapping(value = "/")
    public String index(){
        //model.addAttribute("name", name);
        return "index";
    }
}
