package com.BetterTfrrsWebsite;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import DB.DB;
import DBObjects.Team;
import DBTables.TeamsTable;
import DTOs.MeetDTO;
import DTOs.TeamDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.*;

@Controller
public class TeamController {
    @Value("${last2weeksprsjson}")
    private String last2WeeksPrsJsonPath;
    DB db = new DB();
    TeamsTable teamsTable = new TeamsTable(db);
    @GetMapping("/{teamName}")
    public String teamTest(@PathVariable String teamName, Model model) throws SQLException, UnsupportedEncodingException {
        String decodedTeamName = urlDecode(teamName);
        List<Team> teamsList = teamsTable.getTeamsWithName(decodedTeamName);
        TeamDTO teamDTO = new TeamDTO(teamsList.get(0).name);
        List<MeetDTO> meetDTOS = getLast2WeeksPrs(decodedTeamName);
        model.addAttribute("teamDTO", teamDTO);
        model.addAttribute("meetDTOS", meetDTOS);
        return "teamPage";
    }

    private List<MeetDTO> getLast2WeeksPrs(String teamName) {
        teamName = teamName.replace(" ","-");
        teamName = teamName.toLowerCase();
            //FileReader reader = new FileReader(last2WeeksPrsJsonPath+teamName+"_recentrPRs.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<MeetDTO> meetDTOS;
        try {
            meetDTOS = objectMapper.readValue(new File(last2WeeksPrsJsonPath+teamName+"_recentrPRs.json"), new TypeReference<List<MeetDTO>>() {});
        } catch (IOException e) {
            meetDTOS = List.of();
            e.printStackTrace();
        }
        return meetDTOS;
    }

    private String urlDecode(String urlSafeString) throws UnsupportedEncodingException {
        return URLDecoder.decode(urlSafeString, StandardCharsets.UTF_8);
    }
}

