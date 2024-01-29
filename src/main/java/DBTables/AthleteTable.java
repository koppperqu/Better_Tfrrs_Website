package DBTables;

import DB.DB;
import DBObjects.Athlete;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AthleteTable {
    private Connection connection = null;
    public AthleteTable(DB db) {
        this.connection = db.connection;
    }
    public void tryInsertAthletes(List<Athlete> athletesNoIDS, int team_id) throws SQLException {
        for (Athlete athleteTryInsert : athletesNoIDS){
            List<Athlete> specificAthlete = getAthleteWithAthleteNameAndTeamID(athleteTryInsert.name, team_id);
            //Could be an issue is there are 2 athletes with the same name on the same team.
            boolean athleteExists = specificAthlete.isEmpty();
            if (athleteExists){
                insertAthlete(athleteTryInsert, team_id);
            }
        }
    }

    private void insertAthlete(Athlete athlete, int team_id) throws SQLException {
        String sql = "INSERT INTO ATHLETES (name, link, team_id, year) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, athlete.name);
            stmt.setString(2, athlete.link);
            stmt.setInt(3, team_id);
            stmt.setString(4, athlete.year);
            stmt.executeUpdate();
        }
    }
    public List<Athlete> getAthletes() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM ATHLETES";
        ResultSet rs = stmt.executeQuery(sql);
        return resultSetToAthleteList(rs);
    }
    public List<Athlete> getAthleteWithAthleteNameAndTeamID(String athleteName, int team_id) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM TRACK.ATHLETES WHERE NAME = \"" + athleteName + "\" AND TEAM_ID = "+ team_id;
        ResultSet rs = stmt.executeQuery(sql);
        return resultSetToAthleteList(rs);
    }

    private List<Athlete> resultSetToAthleteList (ResultSet rs) throws SQLException {
        List<Athlete> Athletes = new ArrayList<>();
        while (rs.next()) {
            // Assuming DBObjects.Athlete class has a constructor that takes relevant fields
            Athlete Athlete = new Athlete(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("link"),
                    rs.getInt("team_id"),
                    rs.getString("year")
            );
            Athletes.add(Athlete);
        }
        return Athletes;
    }
}
