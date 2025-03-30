package com.bettertfrrs.db.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Athletes")
public class Athlete {
    @Id
    @GeneratedValue
    @Column
    public int id;
    @Column
    public String name;
    @Column
    public String link;
    @Column
    public int teamId;
    @Column
    public String grade;

    public Athlete() {
    }

    public Athlete(int id, String name, String link, int teamId, String grade) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.teamId = teamId;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", teamId=" + teamId +
                ", grade='" + grade + '\'' +
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

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
