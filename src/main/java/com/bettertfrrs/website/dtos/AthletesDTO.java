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
            //Discovered some team have atheltes with the same name, due to this we need to add the id into the URL so
            //we are able to accuratly return the correct athlete.
            athleteURLs.add("/athletes/"+urlEncoder(conference.name)+"/"+urlEncoder(athlete.team.name)+"/"+urlEncoder(athlete.name)+"/"+athlete.id);
            //athleteURLs.add("/athletes/"+urlEncoder(conference.name)+"/"+urlEncoder(athlete.team.name)+"/"+urlEncoder(athlete.name));
        }
    }

    private String urlEncoder(String s){
        s = URLEncoder.encode(s, Charset.defaultCharset());
        return s;
    }
}
