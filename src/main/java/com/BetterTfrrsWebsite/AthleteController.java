package com.BetterTfrrsWebsite;

import DB.DB;
import DBObjects.Athlete;
import DBObjects.Team;
import DBTables.BestsTable;
import DBTables.TeamsTable;
import DBTables.AthleteTable;
import DTOs.AthleteBestDTO;
import DTOs.AthleteDTO;
import DTOs.BestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class AthleteController {
    DB db = new DB();
    TeamsTable teamsTable = new TeamsTable(db);
    AthleteTable athleteTable = new AthleteTable(db);
    BestsTable bestsTable = new BestsTable(db);

    public UrlGenerator urlGenerator = new UrlGenerator();
    @GetMapping("/{teamName}/athletes")
    public String athletesForTeamPath(@PathVariable String teamName, @RequestParam(required = false) String isMensTeam, Model model) throws SQLException, UnsupportedEncodingException {
        String decodedTeamName = urlDecode(teamName);
        List<Team> teamsList = teamsTable.getTeamsWithName(decodedTeamName);
        if (!teamsList.isEmpty() && (Objects.equals(isMensTeam, "0") ||Objects.equals(isMensTeam, "1") || isMensTeam == null)){
            String teamNameURLSafe = urlGenerator.generateUrl(decodedTeamName);
            List<Athlete> athletesList;
            if (Objects.equals(isMensTeam, "0")) {
                athletesList = athleteTable.getAthletesForTeamNameAndIsMensTeam(decodedTeamName,false);
            } else if (Objects.equals(isMensTeam, "1")) {
                athletesList = athleteTable.getAthletesForTeamNameAndIsMensTeam(decodedTeamName,true);
            } else {
                athletesList = athleteTable.getAthletesForTeamName(decodedTeamName);
            }
            List<AthleteDTO> athleteDTOS = new ArrayList<>();
            for (Athlete athlete : athletesList){
                String nameToPass = "";
                nameToPass = athlete.name;
                AthleteDTO athleteDTO = new AthleteDTO(nameToPass);
                athleteDTO.athleteURL = "/"+teamNameURLSafe+"/athletes/"+athleteDTO.athleteNameURLSafe;
                athleteDTOS.add(athleteDTO);
            }
            model.addAttribute("teamName", decodedTeamName);
            model.addAttribute("athleteDTOS", athleteDTOS);
            //need to give the events page eventName and eventLink, event link will contain the isMensTeam var and team name
            return "teamAthletes";
        }else {
            return "error";
        }
    }

    @GetMapping("/{teamName}/athletes/{athleteName}")
    public String eventsForAthleteOnTeamPath(@PathVariable String teamName, @PathVariable String athleteName, Model model) throws SQLException, UnsupportedEncodingException {
        String decodedTeamName = urlDecode(teamName);
        String decodedAthleteName = urlDecode(athleteName);
        Athlete athlete = athleteTable.getAthleteWithAthleteNameAndTeamName(decodedAthleteName,decodedTeamName);
        List<AthleteBestDTO> athleteBestDTOS = bestsTable.getBestsWithAthleteID(athlete.id);
        model.addAttribute("athleteBestDTOS", athleteBestDTOS);
        model.addAttribute("teamName", decodedTeamName);
        model.addAttribute("athleteName", decodedAthleteName);
        return "athleteBests";
    }


    private String urlDecode(String urlSafeString) throws UnsupportedEncodingException {
        return URLDecoder.decode(urlSafeString, StandardCharsets.UTF_8);
    }
}
