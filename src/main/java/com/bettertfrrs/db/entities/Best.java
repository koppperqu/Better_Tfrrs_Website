package com.bettertfrrs.db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Bests")
@IdClass(BestId.class)
public class Best {

    @Id
    @Column(name = "athleteId")
    private int athleteId;

    @Id
    @Column(name = "eventId")
    private int eventId;

    @ManyToOne
    @JoinColumn(name = "athleteId", referencedColumnName = "id")
    public Athlete athlete;

    @ManyToOne
    @JoinColumn(name = "eventId", referencedColumnName = "id")
    public Event event;

    @Column
    public String mark;
    @Column
    public String link;

    public Best() {
    }

    public Best(Event event, Athlete athlete, String mark, String link) {
        this.event = event;
        this.athlete = athlete;
        this.mark = mark;
        this.link = link;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
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

    @Override
    public String toString() {
        return "Best{" +
                "event=" + event.name +
                ", athlete=" + athlete.name +
                ", mark='" + mark + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
