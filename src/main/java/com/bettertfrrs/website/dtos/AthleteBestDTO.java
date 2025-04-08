package com.bettertfrrs.website.dtos;

public class AthleteBestDTO {
    public String mark;
    public String eventName;
    public String tfrrsBestLink;
    public String eventURL;

    public AthleteBestDTO(String mark, String eventName, String tfrrsBestLink, int eventID, int teamID){
        this.mark = mark;
        this.eventName = eventName;
        this.tfrrsBestLink = tfrrsBestLink;
        this.eventURL = "/" + teamID + "/event/" + eventID;
    }
}
