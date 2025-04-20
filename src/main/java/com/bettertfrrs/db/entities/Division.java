package com.bettertfrrs.db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Divisions")
public class Division {
    @Id
    @GeneratedValue
    @Column
    public int id;
    @Column
    public String name;
    @Column(unique = true)
    public String link;

    public Division() {
    }

    public Division(int id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    @Override
    public String toString() {
        return "Division{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
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
}
