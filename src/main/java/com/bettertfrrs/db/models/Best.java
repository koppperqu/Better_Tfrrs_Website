package com.bettertfrrs.db.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Bests")
@IdClass(BestID.class)
public class Best {
    @Id
    @Column
    public int bestID;
    @Id
    @Column
    public int eventId;
    @Column
    public int athleteId;
    @Column
    public String mark;
    @Column
    public String link;

    public Best() {
    }

    public Best(int bestID, int eventId, int athleteId, String mark, String link) {
        this.bestID = bestID;
        this.eventId = eventId;
        this.athleteId = athleteId;
        this.mark = mark;
        this.link = link;
    }

    @Override
    public String toString() {
        return "Best{" +
                "bestID=" + bestID +
                ", eventId=" + eventId +
                ", athleteId=" + athleteId +
                ", mark='" + mark + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public int getBestID() {
        return bestID;
    }

    public void setBestID(int bestID) {
        this.bestID = bestID;
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

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
