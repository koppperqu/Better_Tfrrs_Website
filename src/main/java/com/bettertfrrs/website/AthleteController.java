package com.bettertfrrs.website;

import com.bettertfrrs.db.entities.Athlete;
import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Team;
import com.bettertfrrs.db.services.AthleteService;
import com.bettertfrrs.db.services.BestService;
import com.bettertfrrs.db.services.ConferenceService;
import com.bettertfrrs.db.services.TeamService;
import com.bettertfrrs.website.dtos.AthleteBestsDTO;
import com.bettertfrrs.website.dtos.AthletesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

@Controller
public class AthleteController {

    ConferenceService conferenceService;
    AthleteService athleteService;
    TeamService teamService;
    BestService bestService;

    @Autowired
    public AthleteController(ConferenceService conferenceService, AthleteService athleteService, TeamService teamService, BestService bestService){
        this.conferenceService = conferenceService;
        this.athleteService = athleteService;
        this.teamService = teamService;
        this.bestService = bestService;
    }

    @GetMapping("/athletes/{encodedConferenceName}/{encodedTeamName}")
    public String athletesForTeam(@PathVariable String encodedConferenceName, @PathVariable String encodedTeamName, @RequestParam(required = false) String isMensTeam, Model model){
        String conferenceName = urlDecoder(encodedConferenceName);
        Optional<Conference> conference = conferenceService.getConferenceByName(conferenceName);
        String teamName = urlDecoder(encodedTeamName);
        Optional<Team> team = teamService.getTeamByName(teamName);
        if (conference.isPresent() && team.isPresent()) {
            Team t = team.get();
            Conference c = conference.get();
            Optional<List<Athlete>> athletes = Optional.empty();
            if (isMensTeam == null){
                athletes = athleteService.getAthletesForTeam(t.id);
            }else if (isMensTeam.equals("0")){
                athletes = athleteService.getAthletesForTeamAndIsMensTeam(t.id, false);
            }else if (isMensTeam.equals("1")){
                athletes = athleteService.getAthletesForTeamAndIsMensTeam(t.id, true);
            }
            if(athletes.isPresent()){
                List<Athlete> a = athletes.get();
                AthletesDTO athletesDTO = new AthletesDTO(a,c);
                model.addAttribute("teamName", t.name);
                model.addAttribute("athletesDTO", athletesDTO);
                return "teamsAthletes";
            }
        }
        return "404";
    }

    @GetMapping("/athletes/{encodedConferenceName}/{encodedTeamName}/{encodedAthleteName}")
    public String bestsForAthlete(@PathVariable String encodedConferenceName, @PathVariable String encodedTeamName, @PathVariable String encodedAthleteName, Model model) {
        String conferenceName = urlDecoder(encodedConferenceName);
        Optional<Conference> conference = conferenceService.getConferenceByName(conferenceName);
        String teamName = urlDecoder(encodedTeamName);
        Optional<Team> team = teamService.getTeamByName(teamName);
        if (team.isPresent() && conference.isPresent()){
            Team t = team.get();
            Conference c = conference.get();
            String athleteName = urlDecoder(encodedAthleteName);
            Optional<Athlete> athlete = athleteService.getAthleteByNameAndTeam(athleteName,t.id);
            if (athlete.isPresent()) {
                Athlete a = athlete.get();
                Optional<List<Best>> bests = bestService.getBestsByAthleteId(a.id);
                if (bests.isPresent()){
                    List<Best> b = bests.get();
                    AthleteBestsDTO athleteBestsDTO = new AthleteBestsDTO(t,c,b);
                    model.addAttribute("athleteBestsDTO", athleteBestsDTO);
                    model.addAttribute("teamName", team.get().name);
                    model.addAttribute("athleteName", athlete.get().name);
                    return "athleteBests";
                }
            }
        }
        return "404";
    }

    private String urlDecoder(String s){
        s = URLDecoder.decode(s, Charset.defaultCharset());
        return s;
    }
}
