package com.bettertfrrs.website;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import com.bettertfrrs.db.entities.Team;
import com.bettertfrrs.db.services.TeamService;
import com.bettertfrrs.website.dtos.MeetDTO;
import com.bettertfrrs.website.dtos.TeamDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.*;

@Controller
public class TeamController {
    @Value("${last2weeksprsjson}")
    private String last2WeeksPrsJsonPath;

    TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/team/{encodedConferenceName}/{encodedTeamName}")
    public String teamPage(@PathVariable String encodedConferenceName,@PathVariable String encodedTeamName, Model model) {
        String teamName = urlDecoder(encodedTeamName);
        Optional<Team> team = teamService.getTeamByName(teamName);
        if (team.isPresent()){
            TeamDTO teamDTO = new TeamDTO(team.get(),encodedConferenceName);
            List<MeetDTO> meetDTOS = getLast2WeeksPrs(teamName);
            model.addAttribute("teamDTO", teamDTO);
            model.addAttribute("meetDTOS", meetDTOS);
            return "teamPage";
        }
        return "404";

    }

    private List<MeetDTO> getLast2WeeksPrs(String teamName) {
        teamName = teamName.replace(" ","-");
        teamName = teamName.toLowerCase();
            //FileReader reader = new FileReader(last2WeeksPrsJsonPath+teamName+"_recentrPRs.json");
        ObjectMapper objectMapper = new ObjectMapper();
        List<MeetDTO> meetDTOS = List.of();
        try {
            meetDTOS = objectMapper.readValue(new File(last2WeeksPrsJsonPath+teamName+"_recentrPRs.json"), new TypeReference<List<MeetDTO>>() {});
        } catch (IOException e) {
            //meetDTOS = List.of();
            //e.printStackTrace();
        }
        return meetDTOS;
    }

    private String urlDecoder(String s){
        s = URLDecoder.decode(s, Charset.defaultCharset());
        return s;
    }
}

