package com.bettertfrrs.website;

import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Team;
import com.bettertfrrs.db.services.ConferenceService;
import com.bettertfrrs.db.services.BestService;
import com.bettertfrrs.db.services.TeamService;
import com.bettertfrrs.website.dtos.ConferenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

@Controller
public class ConferenceController {

    ConferenceService conferenceService;
    TeamService teamService;
    BestService bestService;

    @Autowired
    public ConferenceController(ConferenceService conferenceService, TeamService teamService, BestService bestService){
        this.conferenceService = conferenceService;
        this.teamService = teamService;
        this.bestService = bestService;
    }

    @GetMapping("/conference/{encodedConferenceName}")
    public String conferencesForTeam(@PathVariable String encodedConferenceName, Model model) {
        String conferenceName = urlDecoder(encodedConferenceName);
        Optional<Conference> conference = conferenceService.getConferenceByName(conferenceName);
        if (conference.isPresent()){
            Optional<List<Team>> teams = teamService.getTeamsByConferenceId(conference.get().id);
            if (teams.isPresent()){
                ConferenceDTO conferenceDTO = new ConferenceDTO(conference.get(),teams.get());
                model.addAttribute("conferenceDTO", conferenceDTO);
                return "conferencePage";
            }
        }
        return "404";
    }

    private String urlDecoder(String s){
        s = URLDecoder.decode(s, Charset.defaultCharset());
        return s;
    }
}
