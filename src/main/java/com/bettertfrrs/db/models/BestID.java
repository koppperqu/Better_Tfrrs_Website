package com.bettertfrrs.db.models;

public class BestID {
    private int athleteID;
    private int eventID;

    public BestID() {
    }

    public BestID(int athleteID, int eventID) {
        this.athleteID = athleteID;
        this.eventID = eventID;
    }

    @Override
    public String toString() {
        return "BestID{" +
                "athleteID=" + athleteID +
                ", eventID=" + eventID +
                '}';
    }

    public int getAthleteID() {
        return athleteID;
    }

    public void setAthleteID(int athleteID) {
        this.athleteID = athleteID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
}
