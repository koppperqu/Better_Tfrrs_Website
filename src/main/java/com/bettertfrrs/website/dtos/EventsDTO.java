package com.bettertfrrs.website.dtos;

import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Event;
import com.bettertfrrs.db.entities.Team;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class EventsDTO {

    public List<String> eventNames = new ArrayList<>();
    public List<String> eventURLs = new ArrayList<>();

    public EventsDTO(List<Event> events, Conference conference, Team team, String isMensTeam) {
        String encodedTeamName = urlEncoder(team.name);
        String encodedConferenceName = urlEncoder(conference.name);
        String urlSuffix = "";
        if(isMensTeam != null){
            if(isMensTeam.equals("0")){
                urlSuffix = "?isMensTeam=0";
            }else if(isMensTeam.equals("1")){
                urlSuffix = "?isMensTeam=1";
            }
        }
        for (Event event : events){
            String encodedEventName = urlEncoder(event.shortName);
            eventNames.add(event.name);
            eventURLs.add("/events/" + encodedConferenceName + "/" + encodedTeamName + "/" + encodedEventName + urlSuffix);
        }
    }

    private String urlEncoder(String s){
        s = URLEncoder.encode(s, Charset.defaultCharset());
        return s;
    }
}
