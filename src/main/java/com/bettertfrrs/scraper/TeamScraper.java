package com.bettertfrrs.scraper;

import com.bettertfrrs.db.entities.Conference;
import com.bettertfrrs.db.entities.Team;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class TeamScraper{

    public Team scrapeTeam(Document teamPage, Conference conference) {
        Team team = new Team();
        team.setName(scrapeTeamName(teamPage));
        team.conference = conference;
        team.link = teamPage.location();
        team.hasMen = team.link.contains("_m_");
        team.hasWomen = team.link.contains("_f_");
        return team;
    }

    public List<String> scrapeAthleteLinks(Document teamPage) {
        Element rosterH3 = teamPage.selectFirst("h3:containsOwn(ROSTER)");
        assert rosterH3 != null;
        Element rosterDiv = rosterH3.parent();
        assert rosterDiv != null;
        Element rosterTbody = rosterDiv.selectFirst("tbody");
        assert rosterTbody != null;
        Elements athleteRows = rosterTbody.select("tr");
        List<String> athleteUrls = new ArrayList<>();
        for (Element athleteRow : athleteRows){
            Element td = athleteRow.selectFirst("td");
            assert td != null;
            Element a = td.selectFirst("a");
            assert a != null;
            String athleteHref = a.attr("href");
            if (!athleteHref.contains("https://www.tfrrs.org")) athleteHref = "https://www.tfrrs.org" + athleteHref;
            athleteUrls.add(athleteHref);
        }
        return athleteUrls;
    }

    private String scrapeTeamName(Document teamPage){
        //https://www.tfrrs.org/teams/tf/WI_college_m_Wis_Eau_Claire.html
        String teamLink = teamPage.location();
        int pos1 = teamLink.indexOf("_m_");
        if (pos1 == -1){
            pos1 = teamLink.indexOf("_f_");
        }
        int pos2 = teamLink.indexOf(".html");

        if (pos1 == -1 || pos2 == -1){
            throw new IllegalArgumentException("Unable to extract team name due to link format: " + teamLink);
        }

        return teamLink.substring(pos1+3,pos2).replace("_"," ");
    }
}
