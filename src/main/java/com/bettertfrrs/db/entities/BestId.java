package com.bettertfrrs.db.entities;

import java.io.Serializable;
import java.util.Objects;

public class BestId implements Serializable {

    private int athlete;
    private int event;

    public BestId (){
    }

    public BestId(int athleteId, int eventId) {
        this.athlete = athleteId;
        this.event = eventId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BestId bestId = (BestId) obj;
        return event == bestId.event && athlete == bestId.athlete;
    }

    @Override
    public int hashCode() {
        return Objects.hash(event, athlete);
    }
}