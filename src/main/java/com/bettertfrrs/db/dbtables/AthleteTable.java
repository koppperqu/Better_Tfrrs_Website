//package com.bettertfrrs.db.dbtables;
//
//import com.bettertfrrs.db.DB;
//import com.bettertfrrs.db.entities.Athlete;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AthleteTable {
//    private Connection connection = null;
//    public AthleteTable(DB db) {
//        this.connection = db.connection;
//    }
//    public void tryInsertAthletes(List<Athlete> athletesNoIDS, int team_id) throws SQLException {
//        for (Athlete athleteTryInsert : athletesNoIDS){
//            List<Athlete> specificAthlete = getAthleteWithAthleteNameAndTeamID(athleteTryInsert.name, team_id);
//            //Could be an issue is there are 2 athletes with the same name on the same team.
//            boolean athleteExists = specificAthlete.isEmpty();
//            if (athleteExists){
//                insertAthlete(athleteTryInsert, team_id);
//            }
//        }
//    }
//
//    private void insertAthlete(Athlete athlete, int team_id) throws SQLException {
//        String sql = "INSERT INTO athletes (name, link, team_id, year) VALUES (?, ?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setString(1, athlete.name);
//            stmt.setString(2, athlete.link);
//            stmt.setInt(3, team_id);
//            stmt.setString(4, athlete.grade);
//            stmt.executeUpdate();
//        }
//    }
//    public List<Athlete> getAthletes() throws SQLException {
//        String sql = "SELECT * FROM athletes";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        ResultSet rs = stmt.executeQuery();
//        return resultSetToAthleteList(rs);
//    }
//    public List<Athlete> getAthleteWithAthleteNameAndTeamID(String athleteName, int team_id) throws SQLException {
//        String sql = "SELECT * FROM track.athletes WHERE NAME = ? AND TEAM_ID = ?";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setString(1, athleteName);
//        stmt.setInt(2, team_id);
//        ResultSet rs = stmt.executeQuery();
//        return resultSetToAthleteList(rs);
//    }
//
//    private List<Athlete> resultSetToAthleteList (ResultSet rs) throws SQLException {
//        List<Athlete> Athletes = new ArrayList<>();
//        while (rs.next()) {
//            // Assuming DBObjects.Athlete class has a constructor that takes relevant fields
//            Athlete Athlete = new Athlete(
//                    rs.getInt("id"),
//                    rs.getString("name"),
//                    rs.getString("link"),
//                    rs.getInt("team_id"),
//                    rs.getString("year")
//            );
//            Athletes.add(Athlete);
//        }
//        return Athletes;
//    }
//    //Ned to get athletes for a team, with mens team = to whatever is passed either 1 0 or either
//    public List<Athlete> getAthletesForTeamName(String teamName) throws SQLException {
//        String sql = "SELECT A.* FROM athletes A JOIN teams T WHERE T.ID = A.TEAM_ID AND T.NAME= ?";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setString(1, teamName);
//        ResultSet rs = stmt.executeQuery();
//        return resultSetToAthleteList(rs);
//    }
//
//    public List<Athlete> getAthletesForTeamNameAndIsMensTeam(String teamName, boolean isMensTeam) throws SQLException {
//        String sql = "SELECT A.* FROM athletes A JOIN teams T WHERE T.ID = A.TEAM_ID AND T.NAME = ? AND T.IS_MENS_TEAM = ?";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setString(1, teamName);
//        stmt.setBoolean(2, isMensTeam);
//        ResultSet rs = stmt.executeQuery();
//        return resultSetToAthleteList(rs);
//    }
//
//    public Athlete getAthleteWithAthleteNameAndTeamName(String athleteName, String teamName) throws SQLException {
//        String sql = "SELECT * FROM athletes A JOIN teams T WHERE T.ID = A.TEAM_ID AND A.NAME = ? AND T.NAME = ?";
//        PreparedStatement stmt = connection.prepareStatement(sql);
//        stmt.setString(1, athleteName);
//        stmt.setString(2, teamName);
//        ResultSet rs = stmt.executeQuery();
//        List<Athlete> athletes = resultSetToAthleteList(rs);
//        return athletes.get(0);
//    }
//}
