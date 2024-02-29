package com.BetterTfrrsWebsite;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import DB.DB;
import DBObjects.Team;
import DBTables.TeamsTable;
import DTOs.TeamDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

@Controller
public class TeamController {
    DB db = new DB();
    TeamsTable teamsTable = new TeamsTable(db);
    @GetMapping("/{teamName}")
    public String teamTest(@PathVariable String teamName, Model model) throws SQLException, UnsupportedEncodingException {
        String decodedTeamName = urlDecode(teamName);
        List<Team> teamsList = teamsTable.safeGetTeamsWithName(decodedTeamName);
        TeamDTO teamDTO = new TeamDTO(teamsList.get(0).name);
        model.addAttribute("teamDTO", teamDTO);
        return "teamPage";
    }

    private String urlDecode(String urlSafeString) throws UnsupportedEncodingException {
        return URLDecoder.decode(urlSafeString, StandardCharsets.UTF_8);
    }
}

