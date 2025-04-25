package com.bettertfrrs.scraper;

import com.bettertfrrs.db.entities.Team;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class TeamScraper{

    public Team scrapeTeam(Document teamPage, Integer conferenceId) {
        Team team = new Team();
        team.setName(teamPage.getElementById("team-name").text());
        team.conferenceId = conferenceId;
        team.link = teamPage.location();
        team.hasMen = team.link.contains("_m_");
        team.hasWomen = team.link.contains("_f_");
        return team;
    }

    public List<String> scrapeAthleteLinks(Document teamPage) {
        Element rosterH3 = teamPage.selectFirst("h3:containsOwn(ROSTER)");
        Element rosterDiv = rosterH3.parent();
        Element rosterTbody = rosterDiv.selectFirst("tbody");
        Elements athleteRows = rosterTbody.select("tr");
        List<String> athleteUrls = new ArrayList<>();
        for (Element athleteRow : athleteRows){
            String athleteHref = athleteRow.selectFirst("td").selectFirst("a").attr("href");
            if (!athleteHref.contains("https://www.tfrrs.org")) athleteHref = "https://www.tfrrs.org" + athleteHref;
            athleteUrls.add(athleteHref);
        }
        return athleteUrls;

    }
}
