package com.bettertfrrs.website.dtos;

import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Team;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EventBestsDTO {
    public List<String> marks = new ArrayList<>();
    public List<String> athleteNames = new ArrayList<>();
    public List<String> tfrrsBestLinks = new ArrayList<>();
    public List<String> athleteURLs = new ArrayList<>();

    public EventBestsDTO(List<Best> b, Conference c, Team t) {
        String encodedConferenceName = urlEncoder(c.name);
        String encodedTeamName = urlEncoder(t.name);
        for(Best best : b){
            marks.add(best.mark);
            athleteNames.add(best.athlete.name);
            tfrrsBestLinks.add(best.link);
            String encodedAthleteName = urlEncoder(best.athlete.name);
            athleteURLs.add("/athletes/" + encodedConferenceName + "/" + encodedTeamName + "/" + encodedAthleteName);
        }
    }

    private String urlEncoder(String s){
        s = URLEncoder.encode(s, Charset.defaultCharset());
        return s;
    }
}
