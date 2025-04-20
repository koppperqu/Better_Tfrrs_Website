package com.bettertfrrs.scraper;

import com.bettertfrrs.db.entities.Conference;
import org.jsoup.nodes.Document;

public class ConferenceScraper {
    public Conference scrapeConference(Document conferencePage) {
        Conference conference = new Conference();
        conference.setLink(conferencePage.location());
        conference.setName(getConferenceName(conferencePage));
        return conference;
    }

    private String getConferenceName(Document conferencePage) {
        return conferencePage.getElementsByClass("panel-title").text();
    }
}
