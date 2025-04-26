package com.bettertfrrs.website;

import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.services.ConferenceService;
import com.bettertfrrs.db.services.TeamService;
import com.bettertfrrs.website.dtos.GlobalLinksDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final TeamService teamService;
    private final ConferenceService conferenceService;

    @Autowired
    public GlobalControllerAdvice(TeamService teamService,ConferenceService conferenceService) {
        this.teamService = teamService;
        this.conferenceService = conferenceService;
    }

    @ModelAttribute("globalLinksDTO")
    public GlobalLinksDTO globalLinks(Model model){
        List<Conference> conferences = conferenceService.getAllConferences();
        return new GlobalLinksDTO(conferences,teamService);
    }
}

