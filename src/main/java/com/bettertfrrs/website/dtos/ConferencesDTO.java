package com.bettertfrrs.website.dtos;

import com.bettertfrrs.db.entities.Conference;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ConferencesDTO {
    public List<String> conferenceURLs = new ArrayList<>();
    public List<String> names = new ArrayList<>();

    public ConferencesDTO(List<Conference> conferences){
        for(Conference conference : conferences){
            conferenceURLs.add("/conference/"+urlEncoder(conference.name));
            names.add(conference.name);
        }
    }

    private String urlEncoder(String s){
        s = URLEncoder.encode(s, Charset.defaultCharset());
        return s;
    }
}
