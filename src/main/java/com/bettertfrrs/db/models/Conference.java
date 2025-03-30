package com.bettertfrrs.db.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Conferences")
public class Conference {
    @Id
    @GeneratedValue
    @Column
    public int id;
    @Column
    public String name;
    @Column
    public String link;
    @Column
    public int divisionId;

    public Conference() {
    }

    public Conference(int id, String name, String link, int divisionId) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.divisionId = divisionId;
    }

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                ", divisionId=" + divisionId +
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

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}
