package com.bettertfrrs.website;

import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.services.ConferenceService;
import com.bettertfrrs.website.dtos.ConferencesDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class IndexController {

    private final ConferenceService conferenceService;

    public IndexController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @GetMapping(value = "/")
    public String index(Model model){
        List<Conference> conferences = conferenceService.getAllConferences();
        ConferencesDTO conferencesDTO = new ConferencesDTO(conferences);
        model.addAttribute("conferencesDTO", conferencesDTO);
        return "index";
    }
}
