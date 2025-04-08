package com.bettertfrrs.website.dtos;

import java.io.UnsupportedEncodingException;

public class AthleteDTO {

    public String athleteName;
    public String athleteURL;

    public AthleteDTO(String athleteName, int athleteID, int teamID) {
        this.athleteName = athleteName;
        athleteURL = "/" + teamID + "/athletes/" + athleteID;
    }
}
