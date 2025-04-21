package com.bettertfrrs.db.entities;

import java.io.Serializable;
import java.util.Objects;

public class BestId implements Serializable {

    private int athleteId;
    private int eventId;

    public BestId (){
    }

    public BestId(int athleteId, int eventId) {
        this.athleteId = athleteId;
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BestId bestId = (BestId) obj;
        return eventId == bestId.eventId && athleteId == bestId.athleteId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, athleteId);
    }
}