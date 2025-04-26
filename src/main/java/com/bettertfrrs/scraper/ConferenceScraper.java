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
        assert teamsHeader != null;
        Element teamsDIV = teamsHeader.parent();
        assert teamsDIV != null;
        Element teamsTbody = teamsDIV.selectFirst("tbody");
        assert teamsTbody != null;
        Elements teamsRows = teamsTbody.select("tr");
        List<String> teamUrls = new ArrayList<>();
        for (Element row : teamsRows){
            Elements tds = row.select("td");
            Element a1 = tds.getFirst().selectFirst("a");
            assert a1 != null;
            String mensLink = a1.attr("href");
            teamUrls.add(mensLink);
            Element a2 = tds.get(1).selectFirst("a");
            assert a2 != null;
            String womensLink = a2.attr("href");
            teamUrls.add(womensLink);
        }
        return teamUrls;
    }
}
