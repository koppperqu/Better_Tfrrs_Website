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
    @Column
    public int conferenceId;
    @Column
    public boolean isMensTeam;

    public Team() {
    }

    public Team(int id, String name, String link, int conferenceId, boolean isMensTeam) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.conferenceId = conferenceId;
        this.isMensTeam = isMensTeam;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", conferenceId=" + conferenceId +
                ", isMensTeam=" + isMensTeam +
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
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(int conferenceId) {
        this.conferenceId = conferenceId;
    }

    public boolean isMensTeam() {
        return isMensTeam;
    }

    public void setMensTeam(boolean mensTeam) {
        isMensTeam = mensTeam;
    }
}
