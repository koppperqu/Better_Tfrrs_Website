package com.bettertfrrs.db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Teams")
public class Team {
    @Id
    @GeneratedValue
    @Column
    public int id;
    @Column
    public String name;
    @Column(unique = true)
    public String link;
//    @Column
//    public int conferenceId;
    @ManyToOne
    @JoinColumn(name = "conferenceId", referencedColumnName = "id")
    public Conference conference;
    @Column
    public boolean hasMen;
    @Column
    public boolean hasWomen;

    public Team() {
    }

    public Team(int id, String name, String link, Conference conference, boolean hasMen, boolean hasWomen) {
//    public Team(int id, String name, String link, int conferenceId) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.conference = conference;
        this.hasMen = hasMen;
        this.hasWomen = hasWomen;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", conference=" + conference.name +
                ", hasMen=" + hasMen +
                ", hasWomen=" + hasWomen +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.contains("Wis ")){
            name = name.replace("Wis ","");
        }
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    public int getConferenceId() {
//        return conferenceId;
//    }
//
//    public void setConferenceId(int conferenceId) {
//        this.conferenceId = conferenceId;
//    }

    public boolean isHasMen() {
        return hasMen;
    }

    public void setHasMen(boolean hasMen) {
        this.hasMen = hasMen;
    }

    public boolean isHasWomen() {
        return hasWomen;
    }

    public void setHasWomen(boolean hasWomen) {
        this.hasWomen = hasWomen;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }
}
