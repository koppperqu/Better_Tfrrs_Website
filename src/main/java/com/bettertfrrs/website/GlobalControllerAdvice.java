//package com.bettertfrrs.website;
//
//import com.bettertfrrs.db.DB;
//import com.bettertfrrs.db.entities.Team;
//import com.bettertfrrs.db.dbtables.TeamsTable;
//import com.bettertfrrs.website.dtos.TeamDTO;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ModelAttribute;
//
//import java.io.UnsupportedEncodingException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//@ControllerAdvice
//public class GlobalControllerAdvice {
//    DB db = new DB();
//    TeamsTable teamsTable = new TeamsTable(db);
//    @ModelAttribute("globalLinks")
//    public List<TeamDTO> globalLinks(Model model) throws SQLException, UnsupportedEncodingException {
//        List<Team> teamsList = teamsTable.getTeams();
//        List<TeamDTO> teamsDTOList = new ArrayList<>();
//        TeamDTO tempTeamDTO = null;
//        for (Team team : teamsList) {
//            if (tempTeamDTO == null) {
//                tempTeamDTO = new TeamDTO(team.name);
//            }
//            if (!tempTeamDTO.name.equals(team.name)) {
//                teamsDTOList.add(tempTeamDTO);
//                tempTeamDTO = new TeamDTO(team.name);
//            }
//            if (team.isMensTeam) {
//                tempTeamDTO.mensLink = team.link;
//            } else {
//                tempTeamDTO.womensLink = team.link;
//            }
//        }
//        teamsDTOList.add(tempTeamDTO);
//        model.addAttribute("teamsDTOList", teamsDTOList);
//        return teamsDTOList;
//    }
//}
//
