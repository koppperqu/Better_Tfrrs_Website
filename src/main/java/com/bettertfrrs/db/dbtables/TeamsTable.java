//package com.bettertfrrs.db.dbtables;
//
//import com.bettertfrrs.db.DB;
//import com.bettertfrrs.db.entities.Team;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TeamsTable {
//    private Connection connection = null;
//
//    public TeamsTable(DB db) {
//        this.connection = db.connection;
//    }
//
//    public List<Team> getTeamsWithName(String teamName) throws SQLException {
//        //select * from teams where name = "Eau Claire"
//        String sql = "SELECT * FROM teams WHERE NAME = ?";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setString(1, teamName);
//        ResultSet rs = stmt.executeQuery();
//        return resultSetToTeamList(rs);
//    }
//
//    public void tryInsertTeams(List<Team> teamsNoIDS, int conference_id) throws SQLException {
//        for (Team teamTryInsert : teamsNoIDS){
//            List<Team> specificTeams = getTeamsWithName(teamTryInsert.name);
//            boolean teamExists = false;
//            for (Team team :specificTeams){
//                if (team.isMensTeam == teamTryInsert.isMensTeam) {
//                    teamExists = true;
//                    break;
//                }
//            }
//            if (!teamExists){
//                insertTeam(teamTryInsert, conference_id);
//            }
//        }
//    }
//
//    private void insertTeam(Team team, int conference_id) throws SQLException {
//        //String sql = "INSERT INTO track.teams (name,link,is_Mens_Team,conference_id) VALUES (\"" + team.name + "\",\""+ team.link + "\",\"" + team.isMensTeam + "\"," + conference_id + ")";
//        String sql = "INSERT INTO teams (name, link, conference_id, is_mens_team) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, team.name);
//            stmt.setString(2, team.link);
//            stmt.setInt(3, conference_id);
//            stmt.setInt(4, team.isMensTeam ? 1 : 0); // Convert boolean to 0 or 1
//            stmt.executeUpdate();
//        }
//    }
//    public List<Team> getTeams() throws SQLException {
//        String sql = "SELECT * FROM teams";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        ResultSet rs = stmt.executeQuery();
//        return resultSetToTeamList(rs);
//    }
//
//    public List<Team> getTeamsWithNameAndIsMensTeam(String teamName, boolean isMensTeam) throws SQLException {
//        //Statement stmt = connection.createStatement();
//        //SELECT * FROM TEAMS WHERE TEAMS.NAME = 'Wis.-La Crosse' AND TEAMS.IS_MENS_TEAM = TRUE
//        String sql = "SELECT * FROM track.teams WHERE NAME = ? AND IS_MENS_TEAM = ?";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setString(1, teamName);
//        stmt.setBoolean(2, isMensTeam);
//        ResultSet rs = stmt.executeQuery();
//        return resultSetToTeamList(rs);
//    }
//
//    private List<Team> resultSetToTeamList (ResultSet rs) throws SQLException {
//        List<Team> teams = new ArrayList<>();
//        while (rs.next()) {
//            // Assuming DBObjects.Team class has a constructor that takes relevant fields
//            Team team = new Team(
//                    rs.getInt("id"),
//                    rs.getString("name"),
//                    rs.getString("link"),
//                    rs.getInt("conference_id"),
//                    rs.getBoolean("is_Mens_Team")
//            );
//            teams.add(team);
//        }
//        return teams;
//    }
//}
