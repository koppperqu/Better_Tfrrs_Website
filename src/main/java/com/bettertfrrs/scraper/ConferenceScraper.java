package com.bettertfrrs.scraper;

import com.bettertfrrs.db.entities.Conference;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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

    public List<String> scrapeTeamLinks(Document conferencePage) {
        //We are assuming that there will be teams on each conference page, if not we want
        //to break.
        Element teamsHeader = conferencePage.selectFirst("h3:contains(TEAMS)");
        Element teamsDIV = teamsHeader.parent();
        Element teamsTbody = teamsDIV.selectFirst("tbody");
        Elements teamsRows = teamsTbody.select("tr");
        List<String> teamUrls = new ArrayList<>();
        for (Element row : teamsRows){
            Elements tds = row.select("td");
                String mensLink = tds.get(0).selectFirst("a").attr("href");
                teamUrls.add(mensLink);
                String womensLink = tds.get(1).selectFirst("a").attr("href");
                teamUrls.add(womensLink);
        }
        return teamUrls;
    }
}
