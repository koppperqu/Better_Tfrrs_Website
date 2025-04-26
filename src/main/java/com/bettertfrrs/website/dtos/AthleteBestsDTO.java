package com.bettertfrrs.website.dtos;

import com.bettertfrrs.db.entities.Best;
import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Team;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class AthleteBestsDTO {
    public List<String> marks = new ArrayList<>();
    public List<String> eventNames = new ArrayList<>();
    public List<String> tfrrsBestLinks = new ArrayList<>();
    public List<String> eventURLs = new ArrayList<>();

    public AthleteBestsDTO(Team t, Conference c, List<Best> bests) {
        String encodedTeamName = urlEncoder(t.name);
        String encodedConferenceName = urlEncoder(c.name);
        for(Best best : bests){
            String encodedEventName = urlEncoder(best.event.shortName);
            marks.add(best.mark);
            eventNames.add(best.event.name);
            tfrrsBestLinks.add(best.link);
            eventURLs.add("/events/"+encodedConferenceName+"/"+encodedTeamName+"/"+encodedEventName);
        }
    }

    private String urlEncoder(String s){
        s = URLEncoder.encode(s, Charset.defaultCharset());
        return s;
    }
}
