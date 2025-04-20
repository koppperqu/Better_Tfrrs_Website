package com.bettertfrrs.db.entities;

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
    @Column(unique = true)
    public String link;
    @ManyToOne
    @JoinColumn(name = "teamId", referencedColumnName = "id")
    public Team team;
    @Column
    public String grade;

    public Athlete() {
    }

    public Athlete(int id, String name, String link, Team team, String grade) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.team = team;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Athlete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", teamId=" + team.name +
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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
