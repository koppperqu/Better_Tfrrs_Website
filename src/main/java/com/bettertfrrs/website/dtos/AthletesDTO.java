package com.bettertfrrs.website.dtos;

import com.bettertfrrs.db.entities.Athlete;
import com.bettertfrrs.db.entities.Conference;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AthletesDTO {

    public List<String> athleteNames = new ArrayList<>();
    public List<String> athleteURLs = new ArrayList<>();

    public AthletesDTO(List<Athlete> athletes, Conference conference) {
        for (Athlete athlete : athletes){
            athleteNames.add(athlete.name);
            athleteURLs.add("/athletes/"+urlEncoder(conference.name)+"/"+urlEncoder(athlete.team.name)+"/"+urlEncoder(athlete.name));
        }
    }

    private String urlEncoder(String s){
        s = URLEncoder.encode(s, Charset.defaultCharset());
        return s;
    }
}
