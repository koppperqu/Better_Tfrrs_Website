package com.bettertfrrsdb.dbobjects;

public class Conference {
    public int id;
    public String name;
    public String link;
    public int divisionId;

    public Conference(int id, String name, String link,int divisionId){
        this.id = id;
        this.name = name;
        this.link = link;
        this.divisionId = divisionId;
    }
}
