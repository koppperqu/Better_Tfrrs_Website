package com.bettertfrrs.website.dtos;

import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Team;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ConferenceDTO {
    public String name;
    public List<String> teamNames = new ArrayList<>();
    public List<String> teamURLs = new ArrayList<>();

    public ConferenceDTO(Conference conference, List<Team> teams) {
        this.name = conference.name;
        for (Team team : teams){
            teamNames.add(team.name);
            teamURLs.add("/team/"+urlEncoder(conference.name)+"/"+urlEncoder(team.name));
        }
    }

    private String urlEncoder(String s){
        s = URLEncoder.encode(s, Charset.defaultCharset());
        return s;
    }

}
