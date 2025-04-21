package com.bettertfrrs.website;

import com.bettertfrrs.db.entities.Athlete;
import com.bettertfrrs.db.entities.Team;
import com.bettertfrrs.db.services.AthleteService;
import com.bettertfrrs.db.services.BestService;
import com.bettertfrrs.db.services.TeamService;
import com.bettertfrrs.website.dtos.AthleteBestDTO;
import com.bettertfrrs.website.dtos.AthleteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

@Controller
public class AthleteController {

    AthleteService athleteService;
    TeamService teamService;
    BestService bestService;

    @Autowired
    public AthleteController(AthleteService athleteService,TeamService teamService,BestService bestService){
        this.athleteService = athleteService;
        this.teamService = teamService;
        this.bestService = bestService;
    }

    @GetMapping("/{teamId}/athletes")
    public String athletesForTeam(@PathVariable int teamId, @RequestParam(required = false) Boolean isMensTeam, Model model) throws UnsupportedEncodingException {
//        List<AthleteDTO> athleteDTOs = athleteService.getAthletesByTeamId(teamId, isMensTeam);
//        Optional<Team> team = teamService.getTeamById(teamId);
//        if (!athleteDTOs.isEmpty() & team.isPresent()){
//            model.addAttribute("teamName", team.get().name);
//            model.addAttribute("athleteDTOs", athleteDTOs);
//            return "teamAthletes";
//        }
//        else {
//            return "redirect:/error";
//        }
        return null;
    }

    @GetMapping("/{teamId}/athletes/{athleteId}")
    public String bestsForAthlete(@PathVariable int teamId, @PathVariable int athleteId, Model model) {
        Optional<Athlete> athlete = athleteService.getAthleteById(athleteId);
        Optional<Team> team = teamService.getTeamById(teamId);
        List<AthleteBestDTO> athleteBestDTOs = bestService.getBestsByAthleteId(athleteId);
        if (team.isPresent() & athlete.isPresent()) {
            model.addAttribute("athleteBestDTOs", athleteBestDTOs);
            model.addAttribute("teamName", team.get().name);
            model.addAttribute("athleteName", athlete.get().name);
            return "athleteBests";
        }
        else {
            return "redirect:/error";
        }
    }
}
