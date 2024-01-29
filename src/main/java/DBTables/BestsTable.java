package DBTables;

import DB.DB;
import DBObjects.Best;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BestsTable {
    private Connection connection = null;
    public BestsTable(DB db) {
        this.connection = db.connection;
    }
    public void tryInsertBests(List<Best> bestsNoIDS, int athlete_id) throws SQLException {
        for (Best bestTryInsert : bestsNoIDS){
            List<Best> specificBests = getBestsWithBestAthleteIDAndEventID(bestTryInsert.event_id,athlete_id);
            if (specificBests.isEmpty()){
                insertBest(bestTryInsert, athlete_id);
            }
            else {
                updateBest(specificBests.get(0).id,bestTryInsert.mark, bestTryInsert.link);
            }
        }
    }

    private List<Best> getBestsWithBestAthleteIDAndEventID(int eventId, int athleteId) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM BESTS WHERE ATHLETE_ID = " + eventId + " AND ATHLETE_ID = " + athleteId;
        ResultSet rs = stmt.executeQuery(sql);
        return resultSetToBestList(rs);
    }
    public void updateBest(int bestId, String newMark, String newLink) throws SQLException {
        String sql = "UPDATE BESTS SET mark=?, link=? WHERE id=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, newMark);
            statement.setString(2, newLink);
            statement.setInt(3, bestId);
            statement.executeUpdate();
        }
    }

    private void insertBest(Best best, int athlete_id) throws SQLException {
        String sql = "INSERT INTO BESTS (mark, link, event_id, athlete_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, best.mark);
            stmt.setString(2, best.link);
            stmt.setInt(3, best.event_id);
            stmt.setInt(4, athlete_id);
            stmt.executeUpdate();
        }
    }
    public List<Best> getBests() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM BESTS";
        ResultSet rs = stmt.executeQuery(sql);
        return resultSetToBestList(rs);
    }

    private List<Best> resultSetToBestList (ResultSet rs) throws SQLException {
        List<Best> bests = new ArrayList<>();
        while (rs.next()) {
            // Assuming DBObjects.Best class has a constructor that takes relevant fields
            Best best = new Best(
                    rs.getInt("id"),
                    rs.getString("mark"),
                    rs.getString("link"),
                    rs.getInt("event_id"),
                    rs.getInt("athlete_id")
            );
            bests.add(best);
        }
        return bests;
    }

}
