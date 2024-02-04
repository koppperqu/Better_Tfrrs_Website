package com.BetterTfrrsWebsite;

import DB.DB;
import DBObjects.Team;
import DBTables.TeamsTable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller
public class TeamController {
    DB db = new DB();
    TeamsTable teamsTable = new TeamsTable(db);
    @GetMapping("/{teamName}")
    public String teamTest(@PathVariable String teamName,
                        Model model) throws SQLException {
        List<Team> teamsList = teamsTable.safeGetTeamsWithName(teamName);
        return "teamPage";
    }
}
