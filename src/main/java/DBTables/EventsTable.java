package DBTables;

import DB.DB;
import DBObjects.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventsTable {
    private Connection connection = null;
    public EventsTable(DB db) {
        this.connection = db.connection;
    }
    public void tryInsertEvents(List<Event> eventsNoIDS) throws SQLException {
        for (Event eventTryInsert : eventsNoIDS){
            List<Event> specificEvent = getEventsWithEventShortName(eventTryInsert.short_name);
            if (specificEvent.isEmpty()){
                insertEvent(eventTryInsert);
            }
        }
    }

    private void insertEvent(Event event) throws SQLException {
        String sql = "INSERT INTO EVENTS (name, short_name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, event.name);
            stmt.setString(2, event.short_name);
            stmt.executeUpdate();
        }
    }
    public List<Event> getEvents() throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM EVENTS";
        ResultSet rs = stmt.executeQuery(sql);
        return resultSetToEventList(rs);
    }
    public List<Event> getEventsWithEventShortName(String eventShortName) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = "SELECT * FROM EVENTS WHERE SHORT_NAME = \"" + eventShortName + "\"";
        ResultSet rs = stmt.executeQuery(sql);
        return resultSetToEventList(rs);
    }

    public List<Event> getEventsWithEventNameAndIsMensEvent(String eventName, boolean isMensEvent) throws SQLException {
        Statement stmt = connection.createStatement();
        //SELECT * FROM TEAMS WHERE TEAMS.NAME = 'Wis.-La Crosse' AND TEAMS.IS_MENS_TEAM = TRUE
        String sql = "SELECT * FROM TRACK.TEAMS WHERE NAME = \"" + eventName + "\" AND IS_MENS_TEAM = " + isMensEvent;
        ResultSet rs = stmt.executeQuery(sql);
        return resultSetToEventList(rs);
    }

    private List<Event> resultSetToEventList (ResultSet rs) throws SQLException {
        List<Event> events = new ArrayList<>();
        while (rs.next()) {
            // Assuming DBObjects.Event class has a constructor that takes relevant fields
            Event event = new Event(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("short_name")
            );
            events.add(event);
        }
        return events;
    }

    public int getEventsIDWithShortName(Event event) throws SQLException {
        List<Event> events = getEventsWithEventShortName(event.short_name);
        if (events.isEmpty()){
            insertEvent(event);
            return getEventsWithEventShortName(event.short_name).get(0).id;
        }
        else {
            return events.get(0).id;
        }
    }
}
