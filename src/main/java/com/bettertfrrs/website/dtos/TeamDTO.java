package com.bettertfrrs.website.dtos;
import com.bettertfrrs.db.entities.Team;

import java.net.URLEncoder;
import java.nio.charset.Charset;

public class TeamDTO {
    public String teamLink;
    public String name;

    public TeamDTO(Team team, String encodedConferenceName) {
        this.name = team.name;
        this.teamLink = "/" + encodedConferenceName + "/" + urlEncoder(team.name);
    }

    private String urlEncoder(String s){
        s = URLEncoder.encode(s, Charset.defaultCharset());
        return s;
    }
}
