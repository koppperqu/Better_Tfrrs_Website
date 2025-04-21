package com.bettertfrrs.db.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "Events")
public class Event {
    @Id
    @GeneratedValue
    @Column
    public int id;
    @Column
    public String name;
    @Column
    public String shortName;

    public Event() {
    }

    public Event(int id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", short_name='" + shortName + '\'' +
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
