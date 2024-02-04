package com.BetterTfrrsWebsite;

import DB.DB;
import DBObjects.Team;
import DBTables.TeamsTable;
import DTOs.TeamDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {
    DB db = new DB();
    TeamsTable teamsTable = new TeamsTable(db);
    @ModelAttribute("globalLinks")
    public List<TeamDTO> globalLinks(Model model) throws SQLException {
        List<Team> teamsList = teamsTable.getTeams();
        List<TeamDTO> teamsDTOList = new ArrayList<>();
        TeamDTO tempTeamDTO = new TeamDTO(null);
        for (Team team : teamsList) {
            if (tempTeamDTO.name == null) {
                tempTeamDTO.name = team.name;
            }
            if (!tempTeamDTO.name.equals(team.name)) {
                teamsDTOList.add(tempTeamDTO);
                tempTeamDTO = new TeamDTO(team.name);
            }
            if (team.isMensTeam) {
                tempTeamDTO.mensLink = team.link;
            } else {
                tempTeamDTO.womensLink = team.link;
            }
        }
        teamsDTOList.add(tempTeamDTO);
        model.addAttribute("teamsDTOList", teamsDTOList);
        return teamsDTOList;
    }
}

