package com.bettertfrrs.db.entities;

import java.io.Serializable;
import java.util.Objects;

//Vibe coded class :(

public class BestID implements Serializable {
    private int eventId;
    private int athleteId;

    public BestID() {}

    public BestID(int eventId, int athleteId) {
        this.eventId = eventId;
        this.athleteId = athleteId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(int athleteId) {
        this.athleteId = athleteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BestID bestID = (BestID) o;
        return eventId == bestID.eventId && athleteId == bestID.athleteId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, athleteId);
    }
}